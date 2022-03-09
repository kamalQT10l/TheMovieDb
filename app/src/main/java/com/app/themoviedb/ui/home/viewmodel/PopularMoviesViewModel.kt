package com.app.themoviedb.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.themoviedb.models.Movies
import com.app.themoviedb.repository.MovieDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel@Inject constructor(private val repository: MovieDbRepository):
    ViewModel() {
    fun getPopularMovies() : Flow<PagingData<Movies>> {
        return repository.getPopularMovies().cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }
}