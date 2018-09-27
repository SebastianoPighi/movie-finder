package com.sebastianopighi.data.repositories.datastores

import com.sebastianopighi.data.models.MovieData
import com.sebastianopighi.data.models.MovieDetailsData
import com.sebastianopighi.data.repositories.MoviesCache
import com.sebastianopighi.domain.models.ApiError

class CacheDataStore(private val moviesCache: MoviesCache): MoviesDataStore {

    override fun getMovies(callback: (List<MovieData>?, ApiError?) -> Unit) {
        callback(moviesCache.getAllMovies(), null)
    }

    override fun getMovieDetails(movieId: Int, callback: (MovieDetailsData?, ApiError?) -> Unit) {
        callback(moviesCache.getMovieDetails(movieId), null)
    }


    override fun searchMovie(query: String, callback: (List<MovieData>?, ApiError?) -> Unit) {
        callback(moviesCache.searchMovie(query), null)
    }

    fun saveAllMovies(movieData: List<MovieData>) {
        moviesCache.saveAllMovies(movieData)
    }

    fun saveMovieDetails(movieDetailsData: MovieDetailsData) {
        moviesCache.saveMovieDetails(movieDetailsData)
    }

    fun containsMovie(movieId: Int): Boolean {
        return moviesCache.containsMovie(movieId)
    }

    fun containsMovieDetails(movieId: Int): Boolean {
        return moviesCache.containsMovieDetails(movieId)
    }


    fun isEmpty(): Boolean {
        return moviesCache.isEmpty()
    }

}