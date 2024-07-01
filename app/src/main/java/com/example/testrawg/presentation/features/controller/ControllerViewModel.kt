package com.example.testrawg.presentation.features.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testrawg.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(
    userDataRepository: UserDataRepository,
) : ViewModel() {
    val state: StateFlow<ControllerState> =
        userDataRepository.shouldShowOnboarding
            .map { ControllerState.Data(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ControllerState.Loading,
            )
}
