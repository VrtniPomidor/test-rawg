package com.example.testrawg.data.model

import com.example.testrawg.domain.model.Game
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GamesResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("background_image")
    val imageBackground: String? = null,
    @SerialName("genres")
    val genres: List<GenresResponse>
)

fun GamesResponse.toModel(): Game {
    return Game(
        id = id,
        name = name,
        slug = slug,
        imageBackground = imageBackground ?: "",
        genres = genres.map { it.toModel() }
    )
}
