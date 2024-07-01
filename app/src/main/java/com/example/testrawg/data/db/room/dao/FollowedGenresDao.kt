package com.example.testrawg.data.db.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testrawg.data.db.model.GenresEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowedGenresDao {
    @Query("SELECT * FROM GenresEntity")
    fun getAllFollowedGenres(): Flow<List<GenresEntity>>

    @Query("SELECT * FROM genresEntity WHERE id = :imdbId")
    fun isFollowed(imdbId: String): GenresEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun follow(vararg favorite: GenresEntity)

    @Delete
    suspend fun unFollow(vararg favorite: GenresEntity)
}
