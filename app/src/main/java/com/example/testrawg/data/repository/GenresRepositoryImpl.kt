package com.example.testrawg.data.repository

import com.example.testrawg.data.model.GenresResponse
import com.example.testrawg.data.model.toModel
import com.example.testrawg.data.network.NetworkDataSource
import com.example.testrawg.domain.model.Genre
import com.example.testrawg.domain.repository.GenresRepository
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : GenresRepository {
    override suspend fun getGenres(): List<Genre> {
        return networkDataSource.getGenres().results.map(GenresResponse::toModel)
    }

}
