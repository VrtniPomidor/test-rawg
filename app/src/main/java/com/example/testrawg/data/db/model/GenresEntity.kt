package com.example.testrawg.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GenresEntity(
    @PrimaryKey
    val id: Int,
)
