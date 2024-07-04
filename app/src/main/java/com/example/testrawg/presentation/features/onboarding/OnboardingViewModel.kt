package com.example.testrawg.presentation.features.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testrawg.common.result.Result
import com.example.testrawg.common.result.asResult
import com.example.testrawg.domain.repository.GenresRepository
import com.example.testrawg.domain.repository.UserDataRepository
import com.example.testrawg.domain.usecase.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    getGenresUseCase: GetGenresUseCase,
    private val genresRepository: GenresRepository,
    private val userDataRepository: UserDataRepository,
) : ViewModel() {
    private val refresh = MutableStateFlow(false)

    init {
        getGenres()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val genresUiState: StateFlow<OnboardingState> = refresh
        .flatMapLatest {
            getGenresUseCase()
                .asResult()
                .map { genresResult ->
                    when (genresResult) {
                        is Result.Success -> OnboardingState.Success(genresResult.data)

                        is Result.Loading -> OnboardingState.Loading
                        is Result.Error -> OnboardingState.Error
                    }
                }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = OnboardingState.Loading,
        )


    val isFinishEnabled: StateFlow<Boolean> =
        genresRepository.getFollowedGenres().map { it.isNotEmpty() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false,
            )

    fun getGenres() {
        viewModelScope.launch {
            refresh.emit(!refresh.value)
        }
    }

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
