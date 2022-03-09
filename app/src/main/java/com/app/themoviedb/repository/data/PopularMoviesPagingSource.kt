package com.app.themoviedb.repository.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.themoviedb.models.Movies
import com.app.themoviedb.repository.MoviesDbApiService
import retrofit2.HttpException
import java.io.IOException


private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class PopularMoviesPagingSource(private val moviesDbApiService: MoviesDbApiService)
    : PagingSource<Int, Movies>() {
    override fun getRefreshKey(state: PagingState<Int, Movies>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movies> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = moviesDbApiService.getPopularMovies(MoviesDbApiService.CLIENT_ID, position)
            val movies = response.results
            LoadResult.Page(
                data = movies,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}