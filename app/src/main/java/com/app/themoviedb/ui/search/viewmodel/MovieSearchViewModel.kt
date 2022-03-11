package com.app.themoviedb.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.themoviedb.models.SearchResultData
import com.app.themoviedb.repository.MovieDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(private val repository: MovieDbRepository) : ViewModel() {
    fun searchMovies(query: String) : Flow<PagingData<SearchResultData>> {
        return repository.searchMovies(query).cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }

}