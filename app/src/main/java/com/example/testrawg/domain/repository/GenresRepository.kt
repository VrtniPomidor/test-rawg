package com.example.testrawg.domain.repository

import com.example.testrawg.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface GenresRepository {
    /**
     * List of followed genre ids
     */
    fun getFollowedGenres(): Flow<List<Int>>

    /**
     * Genre list from api
     */
    fun getGenres(): Flow<List<Genre>>

    /**
     * Follow or unfollow genre by id
     *
     * @param genreId Int id to follow/unfollow
     * @param isFollowing Boolean that determines if following or unfollowing
     */
    suspend fun followGenre(genreId: Int, isFollowing: Boolean)
}
