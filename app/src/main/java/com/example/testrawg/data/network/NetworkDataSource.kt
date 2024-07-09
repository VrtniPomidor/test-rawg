package com.example.testrawg.data.network

import com.example.testrawg.data.model.GamesResponse
import com.example.testrawg.data.model.GenresResponse
import com.example.testrawg.data.model.PagingResponse

interface NetworkDataSource {
    suspend fun getGenresNetwork(): PagingResponse<GenresResponse>

    suspend fun getGamesListNetwork(
        search: String? = null,
        genres: String? = null,
        page: Int,
        pageSize: Int,
    ): PagingResponse<GamesResponse>
}
