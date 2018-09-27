package com.sebastianopighi.data.repositories.datastores

import com.sebastianopighi.data.models.MovieData
import com.sebastianopighi.data.models.MovieDetailsData
import com.sebastianopighi.data.networking.MoviesApiImpl
import com.sebastianopighi.domain.models.ApiError

class CloudDataStore(private val api: MoviesApiImpl) : MoviesDataStore {

    override fun getMovies(callback: (List<MovieData>?, ApiError?) -> Unit) {
        api.getMovies { movies, error ->
            callback(movies, error)
        }
    }

    fun getNextMovies(moviesPage: Int, callback: (List<MovieData>?, ApiError?) -> Unit) {
        api.getNextMovies(moviesPage) { movies, error ->
            callback(movies, error)
        }
    }

    override fun getMovieDetails(movieId: Int, callback: (MovieDetailsData?, ApiError?) -> Unit) {
        api.getMovieDetails(movieId) { movie, error ->
            callback(movie, error)
        }
    }

    override fun searchMovie(movieTitleQuery: String, callback: (List<MovieData>?, ApiError?) -> Unit) {
        api.searchMovie(movieTitleQuery) { movies, error ->
            callback(movies, error)
        }
    }

}