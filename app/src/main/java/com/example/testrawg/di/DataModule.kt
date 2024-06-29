package com.example.testrawg.di

import com.example.testrawg.data.repository.UserDataRepositoryImpl
import com.example.testrawg.domain.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: UserDataRepositoryImpl,
    ): UserDataRepository
}
