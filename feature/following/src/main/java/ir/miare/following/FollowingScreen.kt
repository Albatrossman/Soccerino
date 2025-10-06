package ir.miare.following

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ir.miare.domain.model.PlayerWithLeague
import ir.miare.ui.R
import ir.miare.ui.component.AppCenterAlignedTopAppBar
import ir.miare.ui.component.AppHorizontalDivider
import ir.miare.ui.component.EmptyState
import ir.miare.ui.component.LeagueHeader
import ir.miare.ui.component.PlayerRow
import ir.miare.ui.mapper.navigation
import ir.miare.ui.theme.AppTheme
import ir.miare.ui.theme.spacing
import ir.miare.ui.util.plus

@Composable
fun FollowingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: FollowingScreenViewModel = hiltViewModel()
) {

    val state: FollowingScreenState by viewModel.state.collectAsStateWithLifecycle()

    FollowingScreenContent(
        state = state,
        onEvent = { event ->
            when (event) {
                is FollowingScreenEvent.OnPlayerClick -> navController.navigate(
                    route = PlayerWithLeague(
                        player = event.player,
                        league = event.league
                    ).navigation()
                )
            }
        },
        modifier = modifier
    )

}

@Composable
private fun FollowingScreenContent(
    state: FollowingScreenState,
    onEvent: (event: FollowingScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AppCenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.title_following)) }
            )
        }
    ) { innerPadding ->
        if (!state.loading && state.players.isEmpty()) {
            EmptyState(
                title = stringResource(id = R.string.title_empty_state_following),
                subtitle = stringResource(id = R.string.subtitle_empty_state_following)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(all = MaterialTheme.spacing.medium) + innerPadding
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
                                FollowingScreenEvent.OnPlayerClick(
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
        }

        if (state.loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
private fun FollowingScreenPreview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            FollowingScreenContent(
                state = FollowingScreenState(),
                onEvent = {}
            )
        }
    }
}