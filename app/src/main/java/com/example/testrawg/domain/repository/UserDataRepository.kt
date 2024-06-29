package com.example.testrawg.domain.repository

interface UserDataRepository {
    /**
     * Checks whether the user has completed the onboarding process.
     */
    suspend fun shouldShowOnboarding(): Boolean

    /**
     * Sets whether the user has completed the onboarding process.
     */
    suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean)
}
