package com.example.testrawg.data.network

import com.example.testrawg.BuildConfig
import com.example.testrawg.data.model.GenresResponse
import com.example.testrawg.data.model.PagingResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitNetworkApi {
    @GET(value = "genres")
    suspend fun getGenres(): PagingResponse<GenresResponse>
}

@Singleton
class RetrofitNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : NetworkDataSource {

    private val networkApi =
        Retrofit.Builder()
            .baseUrl(RAWG_BASE_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitNetworkApi::class.java)

    override suspend fun getGenres(): PagingResponse<GenresResponse> {
        return networkApi.getGenres()
    }
}

private const val RAWG_BASE_URL = BuildConfig.BACKEND_URL

