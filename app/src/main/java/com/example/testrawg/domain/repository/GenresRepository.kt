package com.example.testrawg.domain.repository

import com.example.testrawg.domain.model.Genre

interface GenresRepository {
    suspend fun getGenres(): List<Genre>
}
