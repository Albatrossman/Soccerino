package ir.miare.soccerino.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.miare.ui.theme.AppTheme
import ir.miare.ui.util.navigation.TopLevelRoute

@Composable
fun MainNavigationBar(
    currentDestination: NavDestination?,
    onTopLevelRouteClick: (route: TopLevelRoute) -> Unit,
    modifier: Modifier = Modifier,
    routes: List<TopLevelRoute> = listOf(
        TopLevelRoute.Ranking,
        TopLevelRoute.Following
    ),
    containerColor: Color = NavigationBarDefaults.containerColor,
    contentColor: Color = MaterialTheme.colorScheme.contentColorFor(backgroundColor = containerColor),
    tonalElevation: Dp = NavigationBarDefaults.Elevation,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets
) {
    val destination: NavDestination? by rememberUpdatedState(newValue = currentDestination)

    NavigationBar(
        modifier = modifier,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        windowInsets = windowInsets,
        content = {
            routes.forEach { route ->
                val selected by remember {
                    derivedStateOf {
                        destination?.hierarchy?.any { it.route == route::class.qualifiedName } == true
                    }
                }

                NavigationBarItem(
                    selected = selected,
                    onClick = { onTopLevelRouteClick(route) },
                    icon = {
                        Icon(
                            painter = painterResource(
                                if (selected) route.selectedIconResId else route.unselectedIconResId
                            ),
                            contentDescription = null
                        )
                    },
                    label = { Text(text = stringResource(route.labelResId)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewMainNavigationBar() {
    AppTheme {
        val navController: NavHostController = rememberNavController()
        val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(initialValue = null)
        val currentDestination = remember(navBackStackEntry) { navBackStackEntry?.destination }

        MainNavigationBar(currentDestination = currentDestination, onTopLevelRouteClick = {})
    }
}