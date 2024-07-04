package com.example.testrawg.presentation.features.gamelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.testrawg.presentation.components.ErrorView
import com.example.testrawg.presentation.components.LoadingIndicator
import com.example.testrawg.presentation.components.TitleBar
import com.example.testrawg.presentation.navigation.GameList

fun NavController.navigateToGameListScreen(navOptions: NavOptions) = navigate(GameList, navOptions)

@Composable
fun GameListScreen(
    onGameDetailsClicked: (Int) -> Unit,
    onSettingsClicked: () -> Unit,
) {
    val viewModel: GameListViewModel = hiltViewModel()
    val state = viewModel.pagingDataFlow.collectAsLazyPagingItems()

    GameListContent(
        state = state,
        onSettingsClicked = onSettingsClicked,
        onGameDetailsClicked = onGameDetailsClicked
    )
}

@Composable
private fun GameListContent(
    state: LazyPagingItems<Game>,
    onSettingsClicked: () -> Unit,
    onGameDetailsClicked: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TitleBar(
            title = stringResource(id = R.string.games_list_title),
            actionIcon = Icons.Filled.Settings,
            actionIconContentDescription = stringResource(id = R.string.settings_icon_content_description),
            onActionClick = onSettingsClicked
        )
        Spacer(modifier = Modifier.height(8.dp))
        GamesList(
            gamesPagingItems = state, onGameClick = onGameDetailsClicked
        )
    }
}

@Composable
private fun GamesList(
    gamesPagingItems: LazyPagingItems<Game>,
    onGameClick: (Int) -> Unit,
) {
    val state = rememberLazyStaggeredGridState()

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(300.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 8.dp,
        state = state,
    ) {
        items(gamesPagingItems.itemCount) { index ->
            val game = gamesPagingItems[index]
            GameCard(
                name = game?.name ?: "",
                imageUrl = game?.imageBackground ?: "",
                genres = game?.genres ?: listOf(),
                onGameClick = { if (game != null) onGameClick(game.id) }
            )
        }
        gamesPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingIndicator() }
                }

                loadState.refresh is LoadState.Error -> {
                    item {
                        ErrorView(retry = { retry() })
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingIndicator() }
                }

                loadState.append is LoadState.Error -> {
                    item {
                        ErrorView(retry = { retry() })
                    }
                }
            }
        }
    }
}

@Composable
private fun GameCard(
    modifier: Modifier = Modifier,
    name: String,
    imageUrl: String,
    genres: List<Genre>,
    onGameClick: () -> Unit,
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
