package com.example.testrawg.data.repository

import com.example.testrawg.domain.repository.UserDataRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor() : UserDataRepository {
    override suspend fun shouldShowOnboarding(): Boolean {
        delay(5000)
        return true
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        return
    }
}
