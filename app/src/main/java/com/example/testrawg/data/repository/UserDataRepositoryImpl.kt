package com.example.testrawg.data.repository

import com.example.testrawg.data.db.datastore.PreferencesDataSource
import com.example.testrawg.di.Dispatcher
import com.example.testrawg.di.RawgDispatchers
import com.example.testrawg.domain.repository.UserDataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource,
    @Dispatcher(RawgDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : UserDataRepository {
    override val shouldShowOnboarding: Flow<Boolean> =
        preferencesDataSource.shouldShowOnboarding

    override suspend fun setShouldShowOnboarding(shouldShowOnboarding: Boolean) {
        preferencesDataSource.setShouldShowOnboarding(shouldShowOnboarding)
    }
}
