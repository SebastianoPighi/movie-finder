package com.sebastianopighi.data.repositories

import com.sebastianopighi.data.models.MovieData
import com.sebastianopighi.data.models.MovieDetailsData

interface MoviesCache {
    fun saveMovie(movieData: MovieData)
    fun removeMovie(movieData: MovieData)
    fun saveAllMovies(movieData: List<MovieData>)
    fun getAllMovies(): List<MovieData>
    fun getMovie(movieId: Int): MovieData
    fun searchMovie(query: String): List<MovieData>
    fun containsMovie(movieId: Int): Boolean
    fun saveMovieDetails(movieData: MovieDetailsData)
    fun removeMovieDetails(movieData: MovieDetailsData)
    fun saveAllMoviesDetails(movieData: List<MovieDetailsData>)
    fun getAllMoviesDetails(): List<MovieDetailsData>
    fun getMovieDetails(movieId: Int): MovieDetailsData
    fun searchMovieDetails(query: String): List<MovieDetailsData>
    fun containsMovieDetails(movieId: Int): Boolean
    fun clear()
    fun isEmpty(): Boolean
}