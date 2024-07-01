package com.example.testrawg.domain.model

data class Genre(
    val id: Int,
    val name: String,
    val slug: String,
    val gamesCount: Int,
    val imageBackground: String,
    val isFollowed: Boolean = false,
)
