package com.example.testrawg.domain.usecase

import com.example.testrawg.domain.repository.GenresRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val genresRepository: GenresRepository,
) {
    operator fun invoke() = flow { emit(genresRepository.getGenres()) }
}
