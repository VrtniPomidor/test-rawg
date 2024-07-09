package com.example.testrawg.presentation.features.gamelist

sealed interface UiAction {
    data class Search(val query: String) : UiAction
    data class ShowSearch(val showSearch: Boolean) : UiAction
}

data class UiState(
    val query: String = DEFAULT_QUERY,
    val isSearchVisible: Boolean = false,
)

const val DEFAULT_QUERY = ""
