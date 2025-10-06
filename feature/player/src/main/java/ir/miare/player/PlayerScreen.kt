package ir.miare.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ir.miare.player.component.InfoRow
import ir.miare.ui.R
import ir.miare.ui.component.AppHorizontalDivider
import ir.miare.ui.component.AppOutlinedButton
import ir.miare.ui.component.AppSheetHeader
import ir.miare.ui.theme.AppTheme
import ir.miare.ui.theme.spacing

@Composable
fun PlayerScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PlayerScreenViewModel = hiltViewModel()
) {

    val state: PlayerScreenState by viewModel.state.collectAsStateWithLifecycle()

    PlayerScreenContent(
        state = state,
        onEvent = { event ->
            when (event) {
                PlayerScreenEvent.OnCloseClick -> navController.navigateUp()
                PlayerScreenEvent.OnFollowClick -> viewModel.handleFollow()
            }
        },
        modifier = modifier
    )

}

@Composable
private fun PlayerScreenContent(
    state: PlayerScreenState,
    onEvent: (event: PlayerScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AppSheetHeader(title = { Text(text = state.playerWithLeague.player.name) })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.spacing.medium,
                    vertical = 24.dp
                ),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            InfoRow(
                icon = painterResource(id = R.drawable.ic_primary_club_bulk_24),
                label = stringResource(id = R.string.label_team).plus(other = ":"),
                value = state.playerWithLeague.player.team.name
            )

            InfoRow(
                icon = painterResource(id = R.drawable.ic_primary_football_bulk_24),
                label = stringResource(id = R.string.label_goals).plus(other = ":"),
                value = state.playerWithLeague.player.totalGoals.toString()
            )

            AppHorizontalDivider()

            Row(horizontalArrangement = Arrangement.spacedBy(space = 16.dp)) {
                AppOutlinedButton(
                    onClick = { onEvent(PlayerScreenEvent.OnCloseClick) },
                    modifier = Modifier.weight(weight = 1.0F),
                    content = {
                        Text(
                            text = stringResource(id = R.string.label_close),
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                )

                AppOutlinedButton(
                    onClick = { onEvent(PlayerScreenEvent.OnFollowClick) },
                    modifier = Modifier.weight(weight = 1.0F),
                    content = {
                        Text(
                            text = stringResource(
                                id = if (state.playerWithLeague.player.followed) {
                                    R.string.label_unfollow
                                } else R.string.label_follow
                            )
                        )
                    }
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PlayerScreenPreview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
//            PlayerScreenContent(
//                state = PlayerScreenState(
//                    playerWithLeague =
//                ),
//                onEvent = {}
//            )
        }
    }
}