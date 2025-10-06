package ir.miare.soccerino

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ir.miare.following.FollowingScreen
import ir.miare.player.PlayerScreen
import ir.miare.ranking.RankingScreen
import ir.miare.ui.util.navigation.Screen
import ir.miare.ui.util.navigation.TopLevelRoute

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = TopLevelRoute.Ranking::class,
        modifier = modifier
    ) {
        navigation(
            startDestination = Screen.Ranking::class,
            route = TopLevelRoute.Ranking::class
        ) {
            composable<Screen.Ranking> {
                RankingScreen(navController = navController)
            }
        }

        navigation(
            startDestination = Screen.Following::class,
            route = TopLevelRoute.Following::class
        ) {
            composable<Screen.Following> {
                FollowingScreen(navController = navController)
            }
        }

        bottomSheet<Screen.Player> {
            PlayerScreen(navController = navController)
        }
    }
}