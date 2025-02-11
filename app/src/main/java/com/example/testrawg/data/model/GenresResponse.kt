package com.example.testrawg.data.model

import com.example.testrawg.domain.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("games_count")
    val gamesCount: Int? = null,
    @SerialName("image_background")
    val imageBackground: String? = null
)

fun GenresResponse.toModel(): Genre {
    return Genre(
        id = id,
        name = name,
        slug = slug,
        gamesCount = gamesCount ?: 0,
        imageBackground = imageBackground ?: ""
    )
}
