package ir.miare.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.miare.ui.theme.AppTheme

@Composable
fun AppCenterAlignedTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                CompositionLocalProvider(
                    value = LocalTextStyle provides MaterialTheme.typography.titleMedium,
                    content = title
                )
            },
            modifier = modifier,
            navigationIcon = navigationIcon,
            actions = actions,
        )

        AppHorizontalDivider()
    }
}

@Preview
@Composable
private fun AppCenterAlignedTopAppBarPreview() {
    AppTheme {
        AppCenterAlignedTopAppBar(
            title = { Text(text = "Players") }
        )
    }
}