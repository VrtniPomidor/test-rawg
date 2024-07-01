package com.example.testrawg.data.db.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.testrawg.di.ApplicationScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("user_preferences")

class PreferencesDataSource @Inject constructor(
    @ApplicationScope appContext: Context
) {
    private val userDataStore = appContext.dataStore
    private val showOnboarding = booleanPreferencesKey("show_onboarding")
    private val onboardingDefaultValue = true

    val shouldShowOnboarding: Flow<Boolean> = userDataStore.data.map { preferences ->
        preferences[showOnboarding] ?: onboardingDefaultValue
    }

    suspend fun setShouldShowOnboarding(shouldShowOnboarding: Boolean) {
        userDataStore.edit {
            it[showOnboarding] = shouldShowOnboarding
        }
    }
}
