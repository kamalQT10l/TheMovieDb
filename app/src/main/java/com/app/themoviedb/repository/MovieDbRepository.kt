package com.app.themoviedb.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDbRepository @Inject constructor(private val moviesDbApiService: MoviesDbApiService) {
    fun getNowPlayingMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
                prefetchDistance = 4,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { MoviePagingSource(moviesDbApiService) }
        ).flow

}