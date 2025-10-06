package ir.miare.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.miare.ui.R
import ir.miare.ui.theme.spacing
import ir.miare.ui.util.TestTag

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.title_empty_state_ranking),
    subtitle: String = stringResource(id = R.string.subtitle_empty_state_ranking)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = MaterialTheme.spacing.large)
            .testTag(tag = TestTag.EMPTY_STATE),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_empty_state),
            contentDescription = stringResource(id = R.string.content_description_empty_state),
            modifier = Modifier.width(width = 200.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = subtitle,
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}