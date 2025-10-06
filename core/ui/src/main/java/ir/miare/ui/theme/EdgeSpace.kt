package ir.miare.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class EdgeSpace(
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp
)

val LocalEdgeSpace = compositionLocalOf { EdgeSpace() }

val MaterialTheme.spacing: EdgeSpace
    @Composable
    @ReadOnlyComposable
    get() = LocalEdgeSpace.current