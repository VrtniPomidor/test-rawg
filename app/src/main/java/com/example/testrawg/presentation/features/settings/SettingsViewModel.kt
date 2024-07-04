package com.example.testrawg.presentation.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testrawg.BuildConfig
import com.example.testrawg.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    val settingsUiState: StateFlow<SettingsState> = userDataRepository.shouldShowOnboarding.map {
        SettingsState(
            showResetOnboarding = BuildConfig.DEBUG,
            isEnabledOnboarding = !it
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsState(),
    )

    fun resetOnboarding() {
        viewModelScope.launch {
            userDataRepository.setShouldShowOnboarding(true)
        }
    }
}
