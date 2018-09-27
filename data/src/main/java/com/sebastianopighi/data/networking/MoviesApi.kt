package com.sebastianopighi.data.networking

import com.sebastianopighi.data.models.MovieDetailsData
import com.sebastianopighi.data.models.MovieListResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/now_playing")
    fun getMovies(@Query("page") page: Int): Call<MovieListResult>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") movieId: Int): Call<MovieDetailsData>

    @GET("search/movie")
    fun searchMovies(@Query("query") query: String): Call<MovieListResult>
}