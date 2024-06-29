package com.example.testrawg.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.testrawg.presentation.features.controller.ControllerScreen
import com.example.testrawg.presentation.features.gamedetails.GameDetailsScreen
import com.example.testrawg.presentation.features.gamedetails.navigateToGameDetails
import com.example.testrawg.presentation.features.gamelist.GameListScreen
import com.example.testrawg.presentation.features.gamelist.navigateToGameListScreen
import com.example.testrawg.presentation.features.onboarding.OnboardingScreen
import com.example.testrawg.presentation.features.onboarding.navigateToOnboardingScreen
import com.example.testrawg.presentation.features.settings.SettingsScreen
import com.example.testrawg.presentation.features.settings.navigateToSettingsScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
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
        composable<Onboarding> {
            OnboardingScreen(
                onFinishOnboarding = {
                    navController.navigateToGameListScreen(navOptions = navOptions {
                        popUpTo(Onboarding) {
                            inclusive = true
                        }
                    })
                }
            )
        }
        composable<GameList> {
            GameListScreen(
                onGameDetailsClicked = { navController.navigateToGameDetails() },
                onSettingsClicked = { navController.navigateToSettingsScreen() }
            )
        }
        composable<GameDetails> {
            GameDetailsScreen(onBackClicked = { navController.navigateUp() })
        }
        composable<Settings> {
            SettingsScreen(onBackClicked = { navController.navigateUp() })
        }
    }
}

@Serializable
object Controller

@Serializable
object Onboarding

@Serializable
object GameList

@Serializable
object GameDetails

@Serializable
object Settings
