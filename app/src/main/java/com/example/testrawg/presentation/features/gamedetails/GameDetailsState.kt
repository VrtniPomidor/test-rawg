package com.example.testrawg.presentation.features.gamedetails

import com.example.testrawg.domain.model.Game

sealed interface GameDetailsUiState {
    data class Success(val game: Game) : GameDetailsUiState
    data object Error : GameDetailsUiState
    data object Loading : GameDetailsUiState
}

sealed interface GameDetailsUiActions {
    data object Retry : GameDetailsUiActions
}
