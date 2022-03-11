package com.app.themoviedb.repository.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.themoviedb.repository.data.local.dao.FavouriteDao
import com.app.themoviedb.repository.data.local.entity.FavouriteMovies

@Database(entities = [FavouriteMovies::class], version = 1)
abstract class MoviesDataBase : RoomDatabase() {
    abstract fun getFavouriteDao() : FavouriteDao
}