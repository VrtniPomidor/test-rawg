package com.example.testrawg.data.model

import com.example.testrawg.common.utils.parseDate
import com.example.testrawg.domain.model.Game
import com.example.testrawg.domain.model.Platform
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GamesResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("background_image")
    val imageBackground: String? = null,
    @SerialName("genres")
    val genres: List<GenresResponse>,
    @SerialName("description")
    val description: String? = null,
    @SerialName("metacritic")
    val metacritic: Int? = null,
    @SerialName("rating")
    val rating: Float? = null,
    @SerialName("ratings_count")
    val ratingsCount: Long? = null,
    @SerialName("released")
    val released: String? = null,
    @SerialName("parent_platforms")
    val parentPlatforms: List<ParentPlatformsResponse>? = null,
)

@Serializable
data class ParentPlatformsResponse(
    @SerialName("platform")
    val platform: PlatformResponse,
)

@Serializable
data class PlatformResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
)

fun GamesResponse.toModel(): Game {
    return Game(
        id = id,
        name = name,
        description = description ?: "",
        slug = slug,
        imageBackground = imageBackground ?: "",
        genres = genres.map { it.toModel() },
        rating = rating,
        ratings = ratingsCount,
        metacritic = metacritic,
        platforms = parentPlatforms?.map { it.platform.toModel() } ?: listOf(),
        releaseDate = if (released != null) parseDate(released) else null
    )
}

fun PlatformResponse.toModel(): Platform {
    return Platform(
        id = id,
        name = name,
    )
}
