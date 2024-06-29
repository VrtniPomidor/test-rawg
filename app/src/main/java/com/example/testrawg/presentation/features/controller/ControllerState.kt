package com.example.testrawg.presentation.features.controller

sealed interface ControllerState {
    data object Loading : ControllerState

    data class Data(val isShowOnboarding: Boolean) : ControllerState
}
