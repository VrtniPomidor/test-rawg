package com.example.testrawg.presentation.features.gamelist

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.testrawg.R
import com.example.testrawg.domain.model.Game
import com.example.testrawg.domain.model.Genre
import com.example.testrawg.presentation.components.ChipsView
import com.example.testrawg.presentation.components.DynamicAsyncImage
import com.example.testrawg.presentation.components.EmptyView
import com.example.testrawg.presentation.components.ErrorView
import com.example.testrawg.presentation.components.LoadingIndicator
import com.example.testrawg.presentation.components.MenuItem
import com.example.testrawg.presentation.components.SearchTextField
import com.example.testrawg.presentation.components.TitleBar
import com.example.testrawg.presentation.navigation.GameList
import kotlinx.coroutines.launch

private const val DEBOUNCE_DEFAULT = 500L
private const val SHOW_TOP_AT_INDEX = 4

fun NavController.navigateToGameListScreen(navOptions: NavOptions) = navigate(GameList, navOptions)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.GameListScreen(
    animatedVisibilityScope: AnimatedContentScope,
    searchDebounce: Long = DEBOUNCE_DEFAULT,
    onGameDetailsClicked: (Long, String) -> Unit,
    onSettingsClicked: () -> Unit,
) {
    val viewModel: GameListViewModel =
        hiltViewModel<GameListViewModel, GameListViewModel.Factory>(
            creationCallback = { factory ->
                factory.create(debounceMs = searchDebounce)
            }
        )
    val pagingDataFlow = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val state by viewModel.state.collectAsStateWithLifecycle()

    GameListContent(
        animatedVisibilityScope = animatedVisibilityScope,
        state = state,
        gamesPagingFlow = pagingDataFlow,
        onSettingsClicked = onSettingsClicked,
        onGameDetailsClicked = onGameDetailsClicked,
        uiActions = viewModel.accept,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.GameListContent(
    animatedVisibilityScope: AnimatedContentScope,
    gamesPagingFlow: LazyPagingItems<Game>,
    onSettingsClicked: () -> Unit,
    onGameDetailsClicked: (Long, String) -> Unit,
    state: UiState,
    uiActions: (UiAction) -> Unit
) {
    BackHandler(state.isSearchVisible) {
        uiActions(hideSearch())
    }

    val lazyGridState = rememberLazyStaggeredGridState()

    val showScrollToTop by remember {
        derivedStateOf {
            lazyGridState.firstVisibleItemIndex > SHOW_TOP_AT_INDEX
        }
    }

    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedVisibility(state.isSearchVisible) {
                SearchTextField(
                    searchQuery = state.query,
                    onSearchQueryChanged = { uiActions(UiAction.Search(it)) },
                    onSearchTriggered = {
                        uiActions(hideSearch())
                        uiActions(UiAction.Search(it))
                    },
                    onBack = { uiActions(hideSearch()) }
                )
            }
            AnimatedVisibility(!state.isSearchVisible) {
                TitleBar(
                    modifier = Modifier,
                    title = stringResource(id = R.string.games_list_title),
                    navigationItem = MenuItem.Search(
                        onClick = { uiActions(showSearch()) }
                    ),
                    actionItem = MenuItem.Settings(
                        onClick = onSettingsClicked
                    ),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            GamesList(
                animatedVisibilityScope = animatedVisibilityScope,
                lazyGridState = lazyGridState,
                gamesPagingItems = gamesPagingFlow,
                onGameClick = onGameDetailsClicked,
            )
        }
        val scope = rememberCoroutineScope()
        AnimatedVisibility(
            showScrollToTop,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 24.dp, end = 16.dp),
        ) {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        lazyGridState.animateScrollToItem(0)
                    }
                }) {
                Text(
                    text = stringResource(id = R.string.go_to_top_button),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@ExperimentalSharedTransitionApi
@Composable
private fun SharedTransitionScope.GamesList(
    animatedVisibilityScope: AnimatedContentScope,
    gamesPagingItems: LazyPagingItems<Game>,
    onGameClick: (Long, String) -> Unit,
    lazyGridState: LazyStaggeredGridState
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(300.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 8.dp,
        state = lazyGridState,
        contentPadding = PaddingValues(bottom = 96.dp),
        modifier = Modifier
    ) {
        gamesPagingItems.apply {
            if (!isRefreshState()) {
                gamesListSuccessContent(this) { game ->
                    GameCard(
                        animatedVisibilityScope = animatedVisibilityScope,
                        name = game?.name ?: "",
                        imageUrl = game?.imageBackground ?: "",
                        imageKey = game?.imageKey ?: "",
                        genres = game?.genres ?: listOf(),
                        onGameClick = { if (game != null) onGameClick(game.id, game.name) }
                    )
                }
            }

            when {
                loadState.refresh is LoadState.Loading -> {
                    item(span = StaggeredGridItemSpan.FullLine) { LoadingIndicator() }
                }

                loadState.refresh is LoadState.Error -> {
                    showErrorView(this)
                }

                loadState.append is LoadState.Loading -> {
                    item(span = StaggeredGridItemSpan.FullLine) { LoadingIndicator() }
                }

                loadState.append is LoadState.Error -> {
                    showErrorView(this)
                }
            }
        }
    }
}

/**
 * Wraps empty item with list of items
 */
private fun LazyStaggeredGridScope.gamesListSuccessContent(
    lazyPagingItems: LazyPagingItems<Game>,
    content: @Composable (Game?) -> Unit
) {
    if (lazyPagingItems.itemCount == 0) {
        item(span = StaggeredGridItemSpan.FullLine) { EmptyView() }
    } else {
        items(
            count = lazyPagingItems.itemCount,
            key = { lazyPagingItems[it]?.id ?: -1 }
        ) { index ->
            val game = lazyPagingItems[index]
            content(game)
        }
    }
}

private fun LazyStaggeredGridScope.showErrorView(
    lazyPagingItems: LazyPagingItems<Game>
) {
    item(span = StaggeredGridItemSpan.FullLine) {
        ErrorView(retry = { lazyPagingItems.retry() })
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.GameCard(
    animatedVisibilityScope: AnimatedContentScope,
    modifier: Modifier = Modifier,
    name: String,
    imageUrl: String,
    imageKey: String,
    genres: List<Genre>,
    onGameClick: () -> Unit,
) {
    ListItem(
        leadingContent = {
            DynamicAsyncImage(
                modifier = Modifier
                    .width(80.dp)
                    .height(60.dp)
                    .sharedElement(
                        state = rememberSharedContentState(
                            key = imageKey
                        ),
                        animatedVisibilityScope = animatedVisibilityScope,
                    ),
                imageUrl = imageUrl,
                contentScale = ContentScale.Crop,
            )
        },
        headlineContent = {
            Text(text = name)
        },
        supportingContent = {
            ChipsView(
                Modifier.fillMaxSize(),
                itemCount = genres.size,
                onText = { genres[it].name }
            )
        },
        modifier = modifier.clickable {
            onGameClick()
        }
    )
}

private fun showSearch(): UiAction.ShowSearch = UiAction.ShowSearch(true)

private fun hideSearch(): UiAction.ShowSearch = UiAction.ShowSearch(false)

private fun LazyPagingItems<Game>.isRefreshState(): Boolean =
    loadState.refresh is LoadState.Loading || loadState.refresh is LoadState.Error
