package com.example.testrawg.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.example.testrawg.presentation.features.controller.ControllerScreen
import com.example.testrawg.presentation.features.gamedetails.GameDetailsScreen
import com.example.testrawg.presentation.features.gamedetails.navigateToGameDetails
import com.example.testrawg.presentation.features.gamelist.GameListScreen
import com.example.testrawg.presentation.features.gamelist.navigateToGameListScreen
import com.example.testrawg.presentation.features.onboarding.OnboardingScreen
import com.example.testrawg.presentation.features.onboarding.navigateToGenreSettingsScreen
import com.example.testrawg.presentation.features.onboarding.navigateToOnboardingScreen
import com.example.testrawg.presentation.features.settings.SettingsScreen
import com.example.testrawg.presentation.features.settings.navigateToSettingsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    SharedTransitionLayout(
        modifier = Modifier,
    ) {
        NavHost(navController, startDestination = Controller) {
            composable<Controller> {
                val navOptions = navOptions {
                    popUpTo(Controller) {
                        inclusive = true
                    }
                }
                ControllerScreen(
                    showOnboarding = {
                        navController.navigateToOnboardingScreen(navOptions = navOptions)
                    },
                    showGamesList = {
                        navController.navigateToGameListScreen(navOptions = navOptions)
                    }
                )
            }
            composable<Onboarding> { backStackEntry ->
                val args: Onboarding = backStackEntry.toRoute()
                val isOnboarding = args.isOnboarding
                OnboardingScreen(
                    isOnboarding = isOnboarding,
                    onFinishOnboarding = {
                        if (isOnboarding) {
                            navController.navigateToGameListScreen(navOptions = navOptions {
                                popUpTo(args) {
                                    inclusive = true
                                }
                            })
                        } else {
                            navController.safeNavigateUp()
                        }
                    }
                )
            }
            composable<GameList> {
                GameListScreen(
                    animatedVisibilityScope = this,
                    onGameDetailsClicked = { id, name ->
                        navController.navigateToGameDetails(
                            gameId = id,
                            gameName = name
                        )
                    },
                    onSettingsClicked = { navController.navigateToSettingsScreen() }
                )
            }
            composable<GameDetails> { backStackEntry ->
                val args: GameDetails = backStackEntry.toRoute()
                GameDetailsScreen(
                    animatedVisibilityScope = this,
                    gameId = args.gameId,
                    gameName = args.gameName,
                    onBackClicked = { navController.safeNavigateUp() })
            }
            composable<Settings> {
                SettingsScreen(
                    onShowGenres = {
                        navController.navigateToGenreSettingsScreen()
                    },
                    onBackClicked = { navController.safeNavigateUp() })
            }
        }
    }
}

fun <T : Any> NavController.safeNavigate(route: T, navOptions: NavOptions? = null) {
    if (currentBackStackEntry?.lifecycleIsResumed() == true) {
        navigate(route, navOptions)
    }
}

private fun NavController.safeNavigateUp() {
    if (currentBackStackEntry?.lifecycleIsResumed() == true) {
        navigateUp()
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
