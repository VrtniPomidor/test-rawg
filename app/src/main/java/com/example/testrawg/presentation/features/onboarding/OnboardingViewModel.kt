package com.example.testrawg.presentation.features.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testrawg.common.result.Result
import com.example.testrawg.common.result.asResult
import com.example.testrawg.domain.repository.GenresRepository
import com.example.testrawg.domain.repository.UserDataRepository
import com.example.testrawg.domain.usecase.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    genresUseCase: GetGenresUseCase,
    private val genresRepository: GenresRepository,
    private val userDataRepository: UserDataRepository,
) : ViewModel() {
    val genresState: StateFlow<OnboardingState> =
        genresUseCase()
            .asResult()
            .map { genresResult ->
                when (genresResult) {
                    is Result.Success -> OnboardingState.Success(genresResult.data)

                    is Result.Loading -> OnboardingState.Loading
                    is Result.Error -> OnboardingState.Error
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = OnboardingState.Loading,
            )

    val isFinishEnabled: StateFlow<Boolean> =
        genresRepository.getFollowedGenres().map { it.isNotEmpty() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false,
            )

    fun onGenreSelect(genreId: Int, isFollowing: Boolean) {
        viewModelScope.launch {
            genresRepository.followGenre(genreId, isFollowing)
        }
    }

    fun onFinishOnboarding() {
        viewModelScope.launch {
            userDataRepository.setShouldShowOnboarding(false)
        }
    }
}
