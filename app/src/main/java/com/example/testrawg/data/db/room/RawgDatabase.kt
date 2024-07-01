package com.example.testrawg.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testrawg.data.db.model.GenresEntity
import com.example.testrawg.data.db.room.dao.FollowedGenresDao

@Database(
    entities = [
        GenresEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class RawgDatabase : RoomDatabase() {
    abstract fun followedGenresDao(): FollowedGenresDao
}
