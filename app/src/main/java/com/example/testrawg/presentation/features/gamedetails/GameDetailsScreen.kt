package com.example.testrawg.presentation.features.gamedetails

import android.widget.TextView
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.testrawg.R
import com.example.testrawg.presentation.components.ChipsView
import com.example.testrawg.presentation.components.DynamicAsyncImage
import com.example.testrawg.presentation.components.ErrorView
import com.example.testrawg.presentation.components.LoadingIndicator
import com.example.testrawg.presentation.components.MenuItem
import com.example.testrawg.presentation.components.TitleBar
import com.example.testrawg.presentation.navigation.GameDetails

fun NavController.navigateToGameDetails(
    gameId: Long,
    gameName: String,
    navOptions: NavOptions? = null
) = navigate(GameDetails(gameId, gameName), navOptions)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.GameDetailsScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    gameId: Long,
    gameName: String,
    onBackClicked: () -> Unit
) {
    val viewModel: GameDetailsViewModel =
        hiltViewModel<GameDetailsViewModel, GameDetailsViewModel.Factory>(
            creationCallback = { factory ->
                factory.create(gameId = gameId)
            }
        )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GameDetailsContent(
        animatedVisibilityScope = animatedVisibilityScope,
        gameName = gameName,
        uiState = uiState,
        onBackClicked = onBackClicked,
        uiActions = viewModel.accept
    )
}

@ExperimentalSharedTransitionApi
@Composable
private fun SharedTransitionScope.GameDetailsContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    gameName: String,
    uiState: GameDetailsUiState,
    onBackClicked: () -> Unit,
    uiActions: (GameDetailsUiActions) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // This makes sure that the game title is not empty while loading,
        // but also that it is using the latest value from backend
        var title by remember { mutableStateOf(gameName) }
        TitleBar(
            modifier = Modifier,
            title = title,
            navigationItem = MenuItem.Back(onClick = onBackClicked),
        )
        when (uiState) {
            is GameDetailsUiState.Success -> {
                title = uiState.game.name
                SuccessState(animatedVisibilityScope, uiState)
            }

            GameDetailsUiState.Error -> ErrorView {
                uiActions(GameDetailsUiActions.Retry)
            }

            GameDetailsUiState.Loading -> LoadingIndicator()
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.SuccessState(
    animatedVisibilityScope: AnimatedVisibilityScope,
    uiState: GameDetailsUiState.Success,
) {
    val scrollState = rememberScrollState()
    val textColor = MaterialTheme.colorScheme.onBackground.toArgb()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        with(uiState.game) {
            DynamicAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
                    .sharedElement(
                        state = rememberSharedContentState(
                            key = imageKey
                        ),
                        animatedVisibilityScope = animatedVisibilityScope,
                    ),
                imageUrl = imageBackground,
                contentScale = ContentScale.Fit
            )
            ChipsView(
                Modifier.fillMaxSize(),
                itemCount = platforms.size,
                onText = { index -> platforms[index].name }
            )
            Text(stringResource(id = R.string.release_date_label, releaseDate ?: ""))
            Text(stringResource(id = R.string.ratings_label, createRatingText()))
            Text(stringResource(id = R.string.metacritic_label, metacritic ?: ""))
            ChipsView(
                Modifier.fillMaxSize(),
                itemCount = genres.size,
                onText = { index -> genres[index].name }
            )
            Text(stringResource(id = R.string.description_label))
            AndroidView(
                modifier = Modifier,
                factory = { context ->
                    val textView = TextView(context)
                    textView.setTextColor(textColor)
                    textView
                },
                update = {
                    it.text =
                        HtmlCompat.fromHtml(
                            description,
                            HtmlCompat.FROM_HTML_MODE_COMPACT
                        )
                }
            )
        }
    }
}
