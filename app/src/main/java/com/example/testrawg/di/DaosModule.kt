package com.example.testrawg.di

import com.example.testrawg.data.db.room.RawgDatabase
import com.example.testrawg.data.db.room.dao.FollowedGenresDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesTopicsDao(
        database: RawgDatabase,
    ): FollowedGenresDao = database.followedGenresDao()
}
