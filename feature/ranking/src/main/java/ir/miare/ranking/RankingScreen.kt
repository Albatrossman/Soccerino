package ir.miare.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ir.miare.domain.model.PlayerWithLeague
import ir.miare.ui.component.LeagueHeader
import ir.miare.ui.component.PlayerRow
import ir.miare.ui.R
import ir.miare.ui.component.AppCenterAlignedTopAppBar
import ir.miare.ui.component.AppHorizontalDivider
import ir.miare.ui.component.EmptyState
import ir.miare.ui.component.PlayerSortDropdownMenu
import ir.miare.ui.mapper.navigation
import ir.miare.ui.theme.AppTheme
import ir.miare.ui.theme.spacing
import ir.miare.ui.util.TestTag
import ir.miare.ui.util.labelResId
import kotlinx.coroutines.flow.map

@Composable
fun RankingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RankingScreenViewModel = hiltViewModel(),
) {

    val state: RankingScreenState by viewModel.state.collectAsStateWithLifecycle()

    PlayersScreenContent(
        state = state,
        onEvent = { event ->
            when (event) {
                RankingScreenEvent.OnRefresh -> viewModel.refresh()
                is RankingScreenEvent.OnSortOptionClick -> {
                    viewModel.sort(sortOption = event.option)
                }
                is RankingScreenEvent.OnPlayerClick -> navController.navigate(
                    route = PlayerWithLeague(
                        player = event.player,
                        league = event.league
                    ).navigation()
                )
                RankingScreenEvent.OnNextPageRequired -> viewModel.next()
            }
        },
        modifier = modifier
    )

}

@Composable
internal fun PlayersScreenContent(
    state: RankingScreenState,
    onEvent: (event: RankingScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val playersListState: LazyListState = rememberLazyListState()

    LaunchedEffect(
        playersListState,
        state.loadingNextPage,
        state.endReached,
        state.refreshing
    ) {
        snapshotFlow { playersListState.layoutInfo }
            .map { info ->
                val total = info.totalItemsCount
                val lastVisible = info.visibleItemsInfo.lastOrNull()?.index ?: 0
                Pair(first = total, second = lastVisible)
            }
            .collect { (total, lastVisible) ->
                val threshold = 3

                if (
                    total > 0 &&
                    lastVisible >= total - 1 - threshold &&
                    !state.loadingNextPage &&
                    !state.endReached &&
                    !state.refreshing
                ) {
                    onEvent(RankingScreenEvent.OnNextPageRequired)
                }
            }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            Column {
                var expanded by remember { mutableStateOf(value = false) }

                AppCenterAlignedTopAppBar(
                    title = { Text(text = stringResource(id = R.string.title_ranking)) }
                )

                Surface(
                    onClick = { expanded = !expanded },
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height = 48.dp)
                                .padding(horizontal = MaterialTheme.spacing.medium),
                            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_primary_filter_light_24),
                                contentDescription = null,
                            )

                            Text(
                                text = stringResource(id = R.string.label_sort_by).plus(other = ":"),
                                style = MaterialTheme.typography.labelSmall
                            )

                            Text(
                                text = stringResource(id = state.sortOption.labelResId),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        PlayerSortDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            selectedOption = state.sortOption,
                            onOptionClick = { option ->
                                onEvent(RankingScreenEvent.OnSortOptionClick(option = option))
                                expanded = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )

                AppHorizontalDivider()
            }
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = state.refreshing,
            onRefresh = { onEvent(RankingScreenEvent.OnRefresh) },
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .testTag(tag = TestTag.RANKING_SCREEN_PULL_TO_REFRESH)
        ) {
            if (!state.loading && state.players.isEmpty()) {
                EmptyState()
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(tag = TestTag.RANKING_SCREEN_PLAYERS_LIST),
                state = playersListState,
                contentPadding = PaddingValues(all = MaterialTheme.spacing.medium)
            ) {
                state.players.forEach { (league, players) ->
                    item(key = league.id.value) {
                        LeagueHeader(
                            title = league.name,
                            rank = league.rank
                        )

                        Spacer(modifier = Modifier.height(height = 16.dp))
                    }

                    itemsIndexed(
                        items = players,
                        key = { index, player -> player.id.value }
                    ) { index, player ->
                        PlayerRow(
                            name = player.name,
                            team = player.team.name,
                            rank = player.team.rank,
                            onMoreClick = {
                                onEvent(
                                    RankingScreenEvent.OnPlayerClick(
                                        player = player,
                                        league = league
                                    )
                                )
                            }
                        )

                        if (index < players.lastIndex) {
                            AppHorizontalDivider()
                        } else {
                            Spacer(modifier = Modifier.height(height = 16.dp))
                        }
                    }
                }

                if (state.loadingNextPage) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height = 48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.testTag(tag = TestTag.RANKING_SCREEN_LOADING_NEXT_PAGE)
                            )
                        }
                    }
                }
            }

            if (state.loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.testTag(tag = TestTag.RANKING_SCREEN_LOADING)
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PlayersScreenPreview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            PlayersScreenContent(
                state = RankingScreenState(),
                onEvent = {}
            )
        }
    }
}