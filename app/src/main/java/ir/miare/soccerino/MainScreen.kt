package ir.miare.soccerino

import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.BottomSheetNavigator
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.ModalBottomSheetLayout
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ir.miare.soccerino.component.MainNavigationBar

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    MainScreenContent(modifier = modifier)

}

@Composable
private fun MainScreenContent(modifier: Modifier = Modifier) {
    val bottomSheetNavigator: BottomSheetNavigator = rememberBottomSheetNavigator()
    val navController: NavHostController = rememberNavController(bottomSheetNavigator)
    val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = navBackStackEntry?.destination

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        modifier = modifier
    ) {
        Scaffold(
            bottomBar = {
                MainNavigationBar(
                    currentDestination = currentDestination,
                    onTopLevelRouteClick = { route ->
                        navController.navigate(route = route) {
                            popUpTo(id = navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        ) { innerPadding ->
            MainNavHost(
                navController = navController,
                modifier = Modifier.padding(paddingValues = innerPadding)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun MainScreenPreview() {
    MainScreenContent()
}