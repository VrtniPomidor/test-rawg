package com.example.testrawg.data.network

import com.example.testrawg.data.model.GenresResponse
import com.example.testrawg.data.model.PagingResponse

interface NetworkDataSource {
    suspend fun getNetworkGenres(): PagingResponse<GenresResponse>
}
