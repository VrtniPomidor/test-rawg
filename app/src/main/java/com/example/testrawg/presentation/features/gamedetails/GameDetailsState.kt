package com.example.testrawg.presentation.features.gamedetails

import com.example.testrawg.domain.model.ErrorType
import com.example.testrawg.domain.model.Game

sealed interface GameDetailsUiState {
    data class Success(val game: Game) : GameDetailsUiState
    data class Error(val errorType: ErrorType) : GameDetailsUiState
    data object Loading : GameDetailsUiState
}

sealed interface GameDetailsUiActions {
    data object Retry : GameDetailsUiActions
}
