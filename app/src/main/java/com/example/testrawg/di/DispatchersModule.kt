package com.example.testrawg.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
internal object DispatchersModule {
    @Provides
    @Dispatcher(RawgDispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(RawgDispatchers.Default)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(@Suppress("unused") val rawgDispatcher: RawgDispatchers)

enum class RawgDispatchers {
    Default,
    IO,
}

