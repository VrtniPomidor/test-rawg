package com.example.testrawg.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.testrawg.data.model.toModel
import com.example.testrawg.data.network.NetworkConstants.NETWORK_PAGE_SIZE
import com.example.testrawg.data.network.NetworkDataSource
import com.example.testrawg.data.paging.GamesPagingSource
import com.example.testrawg.di.Dispatcher
import com.example.testrawg.di.RawgDispatchers.IO
import com.example.testrawg.domain.model.Game
import com.example.testrawg.domain.repository.GamesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : GamesRepository {
    override fun getGames(query: String, followedGenres: List<Int>): Flow<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GamesPagingSource(
                    networkDataSource = networkDataSource,
                    query = query,
                    genres = followedGenres.createSeparatorString()
                )
            }
        ).flow.map { it.map { gamesResponse -> gamesResponse.toModel() } }
    }

    override fun getGameDetails(gameId: Long): Flow<Game> {
        return flow {
            emit(networkDataSource.getGameDetailsNetwork(gameId).toModel())
        }.flowOn(ioDispatcher)

    }
}

private fun List<Int>.createSeparatorString(): String {
    val separator = "," // Separator string
    return joinToString(separator)
}
