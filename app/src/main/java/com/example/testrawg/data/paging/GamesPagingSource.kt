package com.example.testrawg.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testrawg.data.model.GamesResponse
import com.example.testrawg.data.network.NetworkConstants.NETWORK_PAGE_SIZE
import com.example.testrawg.data.network.NetworkDataSource
import retrofit2.HttpException
import java.io.IOException

private const val GAMES_LIST_STARTING_PAGE_INDEX = 1

class GamesPagingSource(
    private val networkDataSource: NetworkDataSource,
    private val query: String,
    private val genres: String,
) : PagingSource<Int, GamesResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GamesResponse> {
        val page = params.key ?: GAMES_LIST_STARTING_PAGE_INDEX
        return try {
            val response = networkDataSource.getGamesListNetwork(
                search = query.takeIf { it.isNotEmpty() },
                genres = genres.takeIf { it.isNotEmpty() },
                page = page,
                pageSize = params.loadSize,
            )
            val games = response.results
            val nextKey = if (games.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                page + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = games,
                prevKey = if (page == GAMES_LIST_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GamesResponse>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}
