package com.example.testrawg.data.network

import com.example.testrawg.BuildConfig
import com.example.testrawg.data.model.GamesResponse
import com.example.testrawg.data.model.GenresResponse
import com.example.testrawg.data.model.PagingResponse
import com.example.testrawg.data.network.NetworkConstants.ID_PATH
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitNetworkApi {
    @GET(value = "genres")
    suspend fun getGenresNetwork(): PagingResponse<GenresResponse>

    @GET(value = "games")
    suspend fun getGamesListNetwork(
        @Query("search") search: String? = null,
        @Query("search_precise") searchPrecise: Boolean = true,
        @Query("genres") genres: String? = null,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 20,
    ): PagingResponse<GamesResponse>

    @GET(value = "games/{$ID_PATH}")
    suspend fun getGameDetailsNetwork(
        @Path(ID_PATH) id: String,
    ): GamesResponse
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

    override suspend fun getGenresNetwork(): PagingResponse<GenresResponse> {
        return networkApi.getGenresNetwork()
    }

    override suspend fun getGamesListNetwork(
        search: String?,
        genres: String?,
        page: Int,
        pageSize: Int,
    ): PagingResponse<GamesResponse> {
        return networkApi.getGamesListNetwork(
            search = search,
            genres = genres,
            page = page,
            pageSize = pageSize,
        )
    }

    override suspend fun getGameDetailsNetwork(gameId: Long): GamesResponse {
        return networkApi.getGameDetailsNetwork(gameId.toString())
    }
}

private const val RAWG_BASE_URL = BuildConfig.BACKEND_URL

