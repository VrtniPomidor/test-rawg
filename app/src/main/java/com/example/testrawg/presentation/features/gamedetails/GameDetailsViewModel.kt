package com.example.testrawg.presentation.features.gamedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testrawg.common.result.Result
import com.example.testrawg.common.result.asResult
import com.example.testrawg.domain.repository.GamesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel(assistedFactory = GameDetailsViewModel.Factory::class)
class GameDetailsViewModel @AssistedInject constructor(
    @Assisted val gameId: Long,
    gamesRepository: GamesRepository
) : ViewModel() {

    val uiState: StateFlow<GameDetailsUiState>

    /**
     * Processor of side effects from the UI which in turn feedback into [uiState]
     */
    val accept: (GameDetailsUiActions) -> Unit

    init {
        val actionStateFlow = MutableSharedFlow<GameDetailsUiActions>()
        val retry = actionStateFlow
            .filterIsInstance<GameDetailsUiActions.Retry>()

            .distinctUntilChanged()
            .onStart { emit(GameDetailsUiActions.Retry) }

        val gameDetails = gamesRepository.getGameDetails(gameId).asResult().map {
            when (it) {
                is Result.Success -> GameDetailsUiState.Success(it.data)

                is Result.Loading -> GameDetailsUiState.Loading
                is Result.Error -> GameDetailsUiState.Error(it.errorType)
            }
        }

        uiState = retry.flatMapLatest { gameDetails }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = GameDetailsUiState.Loading
            )

        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(gameId: Long): GameDetailsViewModel
    }
}
