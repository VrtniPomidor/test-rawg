package com.example.testrawg.domain.model

data class Game(
    val id: Int,
    val name: String,
    val slug: String,
    val imageBackground: String,
    val genres: List<Genre>,
)
