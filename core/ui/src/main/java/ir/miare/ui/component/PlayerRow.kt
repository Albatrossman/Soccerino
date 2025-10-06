package ir.miare.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ir.miare.ui.R
import ir.miare.ui.theme.AppTheme
import ir.miare.ui.util.TestTag

@Composable
fun PlayerRow(
    name: String,
    team: String,
    rank: Int,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 56.dp)
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlayerRank(rank = rank)

        Column(modifier = Modifier.weight(weight = 1.0F)) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.label_team).plus(other = ":"),
                    style = MaterialTheme.typography.labelSmall
                )

                Text(
                    text = team,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        OutlinedIconButton(
            onClick = onMoreClick,
            modifier = Modifier.testTag(tag = "${TestTag.PLAYER_ROW_MORE_NAME}$name"),
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_primary_more_vertical_light_24),
                    contentDescription = null
                )
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun PlayerRowPreview() {
    AppTheme {
        Surface {
            PlayerRow(
                name = "Cristiano Ronaldo",
                team = "Real Madrid",
                rank = 1,
                onMoreClick = {}
            )
        }
    }
}