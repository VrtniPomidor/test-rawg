package com.example.testrawg.presentation.features.controller

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testrawg.presentation.components.LoadingIndicator

@Composable
fun ControllerScreen(
    showOnboarding: () -> Unit,
    showGamesList: () -> Unit
) {
    val viewModel: ControllerViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ControllerContent(
        state = state,
        showOnboarding = showOnboarding,
        showGamesList = showGamesList
    )
}

@Composable
fun ControllerContent(
    state: ControllerState,
    showOnboarding: () -> Unit,
    showGamesList: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoadingIndicator()
    }
    when (state) {
        is ControllerState.Data -> {
            LaunchedEffect(key1 = state.isShowOnboarding) {
                if (state.isShowOnboarding) {
                    showOnboarding()
                } else {
                    showGamesList()
                }
            }
        }

        else -> Unit
    }
}
