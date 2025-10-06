package ir.miare.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ir.miare.ui.theme.AppTheme
import ir.miare.ui.theme.spacing

@Composable
fun AppSheetHeader(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface
) {
    Surface(
        modifier = modifier,
        color = color
    ) {
        Column(
            modifier = Modifier.padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(width = 64.dp)
                    .height(height = 8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = CircleShape
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 64.dp)
                    .padding(horizontal = MaterialTheme.spacing.medium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProvideTextStyle(value = MaterialTheme.typography.titleMedium) {
                    Box(
                        modifier = Modifier.weight(weight = 1.0F),
                        contentAlignment = Alignment.Center
                    ) {
                        title()
                    }
                }
            }

            AppHorizontalDivider()
        }
    }
}

@PreviewLightDark
@Composable
private fun AppSheetHeaderPreview() {
    AppTheme {
        AppSheetHeader(title = { Text(text = "Player Sheet") })
    }
}