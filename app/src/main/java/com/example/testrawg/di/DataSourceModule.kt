package com.example.testrawg.di

import com.example.testrawg.data.network.NetworkDataSource
import com.example.testrawg.data.network.RetrofitNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    fun bindsNetworkDataSource(retrofitNetwork: RetrofitNetwork): NetworkDataSource
}
