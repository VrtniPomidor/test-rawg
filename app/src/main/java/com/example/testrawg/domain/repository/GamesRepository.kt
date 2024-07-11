package com.example.testrawg.domain.repository

import androidx.paging.PagingData
import com.example.testrawg.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getGames(query: String, followedGenres: List<Int>): Flow<PagingData<Game>>
    fun getGameDetails(gameId: Long): Flow<Game>
}
