package com.example.testrawg.presentation.features.onboarding

import com.example.testrawg.domain.model.ErrorType
import com.example.testrawg.domain.model.Genre

sealed interface OnboardingState {
    data object Loading : OnboardingState
    data class Success(val genres: List<Genre>) : OnboardingState
    data class Error(val errorType: ErrorType) : OnboardingState
}
