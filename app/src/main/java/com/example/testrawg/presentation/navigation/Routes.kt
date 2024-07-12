package com.example.testrawg.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object Controller

@Serializable
data class Onboarding(
    val isOnboarding: Boolean
)

@Serializable
object GameList

@Serializable
data class GameDetails(
    val gameId: Long,
    val gameName: String,
)

@Serializable
object Settings
