package com.example.testrawg.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    /**
     * Observe whether the user has completed the onboarding process.
     */
    val shouldShowOnboarding: Flow<Boolean>

    /**
     * Sets whether the user has completed the onboarding process.
     */
    suspend fun setShouldShowOnboarding(shouldShowOnboarding: Boolean)
}
