package com.example.testrawg.domain.usecase

import com.example.testrawg.domain.repository.GenresRepository
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val genresRepository: GenresRepository,
) {
    operator fun invoke() = combine(
        genresRepository.getGenres(),
        genresRepository.getFollowedGenres(),
    ) { genres, followedIds ->
        genres
            .map { genre ->
                genre.copy(isFollowed = genre.id in followedIds)
            }.sortedBy { it.name }
    }
}
