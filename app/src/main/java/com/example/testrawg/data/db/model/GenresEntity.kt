package com.example.testrawg.data.db.model

import androidx.room.PrimaryKey
import com.example.testrawg.data.model.GenresResponse
import com.example.testrawg.domain.model.Genre

data class GenresEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val slug: String,
    val gamesCount: Int,
    val imageBackground: String
)

fun GenresEntity.toModel(value: GenresResponse): Genre {
    with(value) {
        return Genre(
            id = id,
            name = name,
            slug = slug,
            gamesCount = gamesCount,
            imageBackground = imageBackground
        )
    }
}
