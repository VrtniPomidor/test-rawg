package com.example.testrawg.di

import com.example.testrawg.data.repository.GenresRepositoryImpl
import com.example.testrawg.data.repository.UserDataRepositoryImpl
import com.example.testrawg.domain.repository.GenresRepository
import com.example.testrawg.domain.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsUserDataRepository(
        userDataRepository: UserDataRepositoryImpl,
    ): UserDataRepository

    @Binds
    @Singleton
    fun bindsGenresRepository(
        userDataRepository: GenresRepositoryImpl,
    ): GenresRepository
}
