package com.example.testrawg.presentation.features.gamelist

sealed interface UiAction {
    data class Search(val query: String) : UiAction
    data class Scroll(
        val currentQuery: String
    ) : UiAction
}

data class UiState(
    val query: String = DEFAULT_QUERY,
    val lastQueryScrolled: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false,
)

const val DEFAULT_QUERY = ""
