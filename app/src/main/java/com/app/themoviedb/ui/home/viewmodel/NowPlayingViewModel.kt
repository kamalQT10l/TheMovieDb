package com.app.themoviedb.ui.home.viewmodel


import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
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
class NowPlayingViewModel @Inject constructor( private val repository: MovieDbRepository): ViewModel() {

  fun getNowPlayingViewModel() :  Flow<PagingData<Movies>> {
     return repository.getNowPlayingMovies().cachedIn(viewModelScope).flowOn(Dispatchers.IO)
  }




}