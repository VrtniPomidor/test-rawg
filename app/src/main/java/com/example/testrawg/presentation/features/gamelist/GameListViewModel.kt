package com.example.testrawg.presentation.features.gamelist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testrawg.domain.model.Game
import com.example.testrawg.domain.repository.GamesRepository
import com.example.testrawg.domain.repository.GenresRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel(assistedFactory = GameListViewModel.Factory::class)
class GameListViewModel @AssistedInject constructor(
    genresRepository: GenresRepository,
    @Assisted debounceMs: Long = 300,
    private val gamesRepository: GamesRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val state: StateFlow<UiState>

    val pagingDataFlow: Flow<PagingData<Game>>

    /**
     * Processor of side effects from the UI which in turn feedback into [state]
     */
    val accept: (UiAction) -> Unit

    init {
        val initialQuery: String = savedStateHandle[LAST_SEARCH_QUERY] ?: DEFAULT_QUERY

        val actionStateFlow = MutableSharedFlow<UiAction>()
        val showSearch = actionStateFlow
            .filterIsInstance<UiAction.ShowSearch>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.ShowSearch(showSearch = false)) }

        val searches = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.Search(query = initialQuery)) }

        pagingDataFlow = combine(
            searches,
            genresRepository.getFollowedGenres(),
            ::Pair
        ).debounce(debounceMs).flatMapLatest { (action, genres) ->
            getGames(
                queryString = action.query,
                genres = genres
            )
        }.cachedIn(viewModelScope)

        state = combine(
            searches,
            showSearch,
            ::Pair
        ).map { (search, show) ->
            UiState(
                query = search.query,
                isSearchVisible = show.showSearch
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState()
        )

        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
    }

    private fun getGames(queryString: String, genres: List<Int>): Flow<PagingData<Game>> =
        gamesRepository.getGames(queryString, genres)

    override fun onCleared() {
        savedStateHandle[LAST_SEARCH_QUERY] = state.value.query
        super.onCleared()
    }

    @AssistedFactory
    fun interface Factory {
        fun create(debounceMs: Long): GameListViewModel
    }
}

private const val LAST_SEARCH_QUERY: String = "last_search_query"
