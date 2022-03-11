package com.app.themoviedb.repository.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_table")
data class FavouriteMovies(@PrimaryKey(autoGenerate = true) val id:Int=0,
                           val title:String, val tagLine:String,
                           val movieId: Int,
                           val releaseDate:String,
                           val voteAvg: Double,
                           val genre: String,
                           val overView:String,
                           val posterPath:String)