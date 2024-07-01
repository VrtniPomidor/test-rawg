package com.example.testrawg.di

import android.content.Context
import androidx.room.Room
import com.example.testrawg.data.db.room.RawgDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesRawgDatabase(
        @ApplicationContext context: Context,
    ): RawgDatabase = Room.databaseBuilder(
        context,
        RawgDatabase::class.java,
        "rawg-database",
    ).build()
}
