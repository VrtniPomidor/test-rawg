package com.example.testrawg.domain.usecase

import com.example.testrawg.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShouldShowOnboardingUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository
) {
    operator fun invoke(): Flow<Boolean> = flow {
        emit(userDataRepository.shouldShowOnboarding())
    }
}
