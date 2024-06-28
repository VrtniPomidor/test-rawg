package com.example.testrawg.data.network

import com.example.testrawg.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

class RetrofitRawgNetworkApi {
}

@Singleton
internal class RetrofitRawgNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : RawgNetworkDataSource {

    private val networkApi =
        Retrofit.Builder()
            .baseUrl(RAWG_BASE_URL)
            // We use callFactory lambda here with dagger.Lazy<Call.Factory>
            // to prevent initializing OkHttp on the main thread.
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitRawgNetworkApi::class.java)
}

private const val RAWG_BASE_URL = BuildConfig.BACKEND_URL

