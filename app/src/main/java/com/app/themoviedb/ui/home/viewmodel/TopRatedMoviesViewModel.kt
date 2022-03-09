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
class TopRatedMoviesViewModel@Inject constructor(private val repository: MovieDbRepository):
    ViewModel() {
    fun getTopRatedMovies() : Flow<PagingData<Movies>> {
        return repository.getTopRatedMovies().cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }
}