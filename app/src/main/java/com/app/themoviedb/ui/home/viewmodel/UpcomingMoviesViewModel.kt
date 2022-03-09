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
class UpcomingMoviesViewModel@Inject constructor(private val repository: MovieDbRepository):
    ViewModel() {
    fun getUpcomingMovies() : Flow<PagingData<Movies>> {
        return repository.getUpcomingMovies().cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }
}