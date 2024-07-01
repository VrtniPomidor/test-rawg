package com.example.testrawg.data.repository

import com.example.testrawg.data.db.datastore.PreferencesDataSource
import com.example.testrawg.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource,
) : UserDataRepository {
    override val shouldShowOnboarding: Flow<Boolean> =
        preferencesDataSource.shouldShowOnboarding

    override suspend fun setShouldShowOnboarding(shouldShowOnboarding: Boolean) {
        preferencesDataSource.setShouldShowOnboarding(shouldShowOnboarding)
    }
}
