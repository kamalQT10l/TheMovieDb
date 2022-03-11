package com.app.themoviedb.ui.details.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.themoviedb.models.MovieDetailResponse
import com.app.themoviedb.repository.MovieDbRepository
import com.app.themoviedb.repository.data.local.dao.FavouriteDao
import com.app.themoviedb.repository.data.local.entity.FavouriteMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository: MovieDbRepository,
private val favouriteDao: FavouriteDao): ViewModel(){



    val movieDetailLiveData  = MutableLiveData<MovieDetailResponse>()

    fun getMovieDetail(movieId : Int){
        viewModelScope.launch {
            repository.getMovieDetails(movieId).enqueue(object : Callback<MovieDetailResponse> {
                override fun onFailure(call: Call<MovieDetailResponse>?, t: Throwable?) {

                }
                override fun onResponse(call: Call<MovieDetailResponse>?, response: Response<MovieDetailResponse>?) {
                    movieDetailLiveData.value = response?.body()
                }
            })
        }
    }

    fun addMovieToFavourite(favouriteMovies: FavouriteMovies) {
        viewModelScope.launch {
            favouriteDao.insertFavMovie(favouriteMovies)
        }
    }

    fun removeMovieFromFavourites(favouriteMovies: FavouriteMovies) {
        viewModelScope.launch {
            favouriteDao.deleteFavMovie(favouriteMovies)
        }
    }
}