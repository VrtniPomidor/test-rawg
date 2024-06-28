package com.example.testrawg.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Controller) {
        composable<Controller> {
            
        }
        composable<Onboarding> {

        }
        composable<GameList> {

        }
        composable<GameDetails> {

        }
        composable<Settings> {

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
