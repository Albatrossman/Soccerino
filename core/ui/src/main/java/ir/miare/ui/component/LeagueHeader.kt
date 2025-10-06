package ir.miare.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ir.miare.ui.theme.AppTheme

@Composable
fun LeagueHeader(
    title: String,
    rank: Int,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium
) {
    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 56.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,

                style = textStyle
            )

            LeagueRank(rank = rank)
        }
    }
}



@PreviewLightDark
@Composable
private fun LeagueHeaderPreview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Box(Modifier.padding(all = 24.dp)) {
                LeagueHeader(title = "La Liga", rank = 3)
            }
        }
    }
}