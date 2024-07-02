package com.example.testrawg.presentation.features.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.testrawg.R
import com.example.testrawg.domain.model.Genre
import com.example.testrawg.presentation.components.AdaptiveButton
import com.example.testrawg.presentation.components.CustomIconToggleButton
import com.example.testrawg.presentation.components.DynamicAsyncImage
import com.example.testrawg.presentation.components.ErrorView
import com.example.testrawg.presentation.components.LoadingIndicator
import com.example.testrawg.presentation.components.TitleBar
import com.example.testrawg.presentation.navigation.Onboarding

fun NavController.navigateToOnboardingScreen(navOptions: NavOptions? = null) =
    navigate(Onboarding(isOnboarding = true), navOptions)

fun NavController.navigateToGenreSettingsScreen(navOptions: NavOptions? = null) =
    navigate(Onboarding(isOnboarding = false), navOptions)

@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit
) {
    val viewModel: OnboardingViewModel = hiltViewModel()
    val genresState by viewModel.genresUiState.collectAsStateWithLifecycle()
    val isFinishEnabled by viewModel.isFinishEnabled.collectAsStateWithLifecycle()
    OnboardingContent(
        genresState = genresState,
        onGenreSelect = viewModel::onGenreSelect,
        isFinishEnabled = isFinishEnabled,
        onFinishOnboarding = {
            viewModel.onFinishOnboarding()
            onFinishOnboarding()
        },
        onRetry = viewModel::getGenres
    )
}

@Composable
fun OnboardingContent(
    genresState: OnboardingState,
    isFinishEnabled: Boolean,
    onGenreSelect: (Int, Boolean) -> Unit,
    onFinishOnboarding: () -> Unit,
    onRetry: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        when (genresState) {
            OnboardingState.Error -> {
                ErrorView(retry = onRetry)
            }

            OnboardingState.Loading -> {
                LoadingIndicator()
            }

            is OnboardingState.Success -> {
                TitleBar(stringResource(R.string.select_genres_you_are_interested_in_title))
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    GenresList(genres = genresState.genres, onGenreSelect = onGenreSelect)
                }
                Spacer(modifier = Modifier.height(8.dp))
                AdaptiveButton(isEnabled = isFinishEnabled, onClick = onFinishOnboarding) {
                    Text(stringResource(id = R.string.done_button))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun GenresList(
    genres: List<Genre>,
    onGenreSelect: (Int, Boolean) -> Unit,
) {
    val state = rememberLazyStaggeredGridState()

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(300.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 8.dp,
        state = state,
    ) {
        genres.forEach { genre ->
            item(key = genre.id) {
                GenresCard(
                    name = genre.name,
                    imageUrl = genre.imageBackground,
                    isFollowing = genre.isFollowed,
                    onGenreSelect = { onGenreSelect(genre.id, it) }
                )
            }
        }
    }
}

@Composable
private fun GenresCard(
    modifier: Modifier = Modifier,
    name: String,
    imageUrl: String,
    isFollowing: Boolean,
    onGenreSelect: (Boolean) -> Unit,
) {
    ListItem(
        leadingContent = {
            DynamicAsyncImage(
                modifier = Modifier
                    .width(80.dp)
                    .height(60.dp),
                imageUrl = imageUrl
            )
        },
        headlineContent = {
            Text(text = name)
        },
        trailingContent = {
            CustomIconToggleButton(
                checked = isFollowing,
                onCheckedChange = onGenreSelect,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(
                            id = R.string.genres_follow_button_content_desc,
                        ),
                    )
                },
                checkedIcon = {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = stringResource(
                            id = R.string.genres_unfollow_button_content_desc,
                        ),
                    )
                },
            )
        },
        modifier = modifier
    )
}
