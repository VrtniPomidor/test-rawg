package com.example.testrawg.di

import android.content.Context
import com.example.testrawg.data.db.datastore.PreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun providesPreferencesDataStore(
        @ApplicationContext appContext: Context,
    ): PreferencesDataSource = PreferencesDataSource(appContext)
}
