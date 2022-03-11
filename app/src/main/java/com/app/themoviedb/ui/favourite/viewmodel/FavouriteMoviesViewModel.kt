package com.app.themoviedb.ui.favourite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.themoviedb.repository.data.local.dao.FavouriteDao
import com.app.themoviedb.repository.data.local.entity.FavouriteMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteMoviesViewModel @Inject constructor(
    private val favouriteDao: FavouriteDao
): ViewModel() {

    val favMovies : LiveData<List<FavouriteMovies>>
        get() {
            return favouriteDao.getFavouriteMovies().asLiveData()
        }
}