package com.app.themoviedb.repository.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.themoviedb.models.SearchResultData
import com.app.themoviedb.repository.api.MoviesDbApiService
import retrofit2.HttpException
import java.io.IOException


private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class SearchMoviesPagingSource(private val moviesDbApiService: MoviesDbApiService, private val query: String)
    : PagingSource<Int, SearchResultData>() {
    override fun getRefreshKey(state: PagingState<Int, SearchResultData>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResultData> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = moviesDbApiService.searchMovies(MoviesDbApiService.CLIENT_ID, query,position)
            val movies = response.searchResultData
            LoadResult.Page(
                data = movies,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies == null || movies.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}