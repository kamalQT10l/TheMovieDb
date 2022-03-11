package com.app.themoviedb.repository.data.local.dao

import androidx.room.*
import com.app.themoviedb.repository.data.local.entity.FavouriteMovies
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM fav_table")
    fun getFavouriteMovies(): Flow<List<FavouriteMovies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavMovie(movies: FavouriteMovies)

    @Delete
    suspend fun deleteFavMovie(movies: FavouriteMovies)
}