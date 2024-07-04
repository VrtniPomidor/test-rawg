package com.example.testrawg.data.repository

import com.example.testrawg.data.db.model.GenresEntity
import com.example.testrawg.data.db.room.dao.FollowedGenresDao
import com.example.testrawg.data.model.toModel
import com.example.testrawg.data.network.NetworkDataSource
import com.example.testrawg.di.Dispatcher
import com.example.testrawg.di.RawgDispatchers.IO
import com.example.testrawg.domain.model.Genre
import com.example.testrawg.domain.repository.GenresRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val followedGenresDao: FollowedGenresDao,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : GenresRepository {
    override fun getFollowedGenres(): Flow<List<Int>> =
        followedGenresDao.getAllFollowedGenres()
            .map { it.map { genre -> genre.id } }
            .flowOn(ioDispatcher)

    override fun getGenres(): Flow<List<Genre>> {
        return flow {
            emit(networkDataSource.getGenresNetwork().results.map { it.toModel() })
        }.flowOn(ioDispatcher)
    }

    override suspend fun followGenre(genreId: Int, isFollowing: Boolean) {
        withContext(ioDispatcher) {
            val followedGenre = GenresEntity(genreId)
            if (isFollowing) {
                followedGenresDao.follow(followedGenre)
            } else {
                followedGenresDao.unFollow(followedGenre)
            }
        }
    }
}
