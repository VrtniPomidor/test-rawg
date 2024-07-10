package com.example.testrawg.domain.model

import com.example.testrawg.common.utils.areAnyNull

data class Game(
    val id: Long,
    val name: String,
    val description: String,
    val slug: String,
    val imageBackground: String,
    val genres: List<Genre>,
    val rating: Float?,
    val ratings: Long?,
    val metacritic: Int?,
    val platforms: List<Platform> = listOf(),
    val releaseDate: String? = null,
) {
    val imageKey = "image-$imageBackground"

    fun createRatingText() = if (areAnyNull(rating, ratings)) {
        ""
    } else {
        "$rating/5 ($ratings)"
    }

}

data class Platform(
    val id: Int,
    val name: String,
)
