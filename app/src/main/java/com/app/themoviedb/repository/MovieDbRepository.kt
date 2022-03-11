package com.app.themoviedb.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.app.themoviedb.models.MovieDetailResponse
import com.app.themoviedb.repository.data.MoviePagingSource
import com.app.themoviedb.repository.data.PopularMoviesPagingSource
import com.app.themoviedb.repository.data.TopRatedMoviesPagingSource
import com.app.themoviedb.repository.data.UpcomingMoviesPagingSource
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDbRepository @Inject constructor(private val moviesDbApiService: MoviesDbApiService) {

    fun getMovieDetails(movieId: Int) : Call<MovieDetailResponse> {
        return moviesDbApiService.getDetailsOfTheMovie(movieId,MoviesDbApiService.CLIENT_ID)
    }

    fun getNowPlayingMovies() = Pager(config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
                prefetchDistance = 4,
                initialLoadSize = 40), pagingSourceFactory = { MoviePagingSource(moviesDbApiService) }).flow

    fun getPopularMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
                prefetchDistance = 4,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { PopularMoviesPagingSource(moviesDbApiService) }
        ).flow

    fun getTopRatedMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
                prefetchDistance = 4,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { TopRatedMoviesPagingSource(moviesDbApiService) }
        ).flow

    fun getUpcomingMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
                prefetchDistance = 4,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { UpcomingMoviesPagingSource(moviesDbApiService) }
        ).flow

}