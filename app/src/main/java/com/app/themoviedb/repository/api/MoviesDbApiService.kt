package com.app.themoviedb.repository.api

import com.app.themoviedb.BuildConfig
import com.app.themoviedb.models.MovieData
import com.app.themoviedb.models.MovieDetailResponse
import com.app.themoviedb.models.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesDbApiService {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val CLIENT_ID = BuildConfig.MOVIE_DB_API_KEY
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
    }

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String, @Query("page") pageNumber: Int ): MovieData

    @GET("movie/now_playing")
    suspend fun getNowShowingMovies(@Query("api_key") apiKey: String,
                            @Query("page") pageNumber: Int
                           ): MovieData

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String,
                          @Query("page") pageNumber: Int
                          ): MovieData

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String,
                          @Query("page") pageNumber: Int
                         ): MovieData



    @GET("movie/{movieId}")
    fun getDetailsOfTheMovie(@Path("movieId") movieId: Int, @Query("api_key") apiKey: String): Call<MovieDetailResponse>


    @GET("search/movie")
    suspend fun searchMovies(@Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): SearchResponse

}