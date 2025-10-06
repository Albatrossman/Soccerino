package ir.miare.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ir.miare.ui.R
import ir.miare.ui.theme.AppTheme
import ir.miare.ui.theme.StarShape
import ir.miare.ui.theme.prestige

@Composable
fun PlayerRank(
    rank: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = contentColorFor(backgroundColor = color),
    textStyle: TextStyle = MaterialTheme.typography.labelSmall
) {
    Surface(
        modifier = modifier,
        shape = StarShape(),
        color = color,
        contentColor = contentColor
    ) {
        Text(
            text = rank.toString(),
            modifier = Modifier.padding(all = 8.dp),
            style = textStyle
        )
    }
}

@Preview
@Composable
private fun PlayerRankPreview() {
    AppTheme {
        PlayerRank(rank = 1)
    }
}

@Composable
fun LeagueRank(
    rank: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.prestige,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall
) {
    Row(
        modifier = modifier
            .background(color = color.copy(alpha = 0.08F))
            .border(
                width = Dp.Hairline,
                color = color,
                shape = MaterialTheme.shapes.extraSmall
            )
            .padding(horizontal = 4.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_primary_cup_bulk_24),
            contentDescription = null,
            modifier = Modifier.size(size = 18.dp),
            tint = color
        )

        Text(
            text = rank.toString(),
            style = textStyle,
        )
    }
}

@Preview
@Composable
private fun LeagueRankPreview() {
    AppTheme {
        LeagueRank(rank = 1)
    }
}