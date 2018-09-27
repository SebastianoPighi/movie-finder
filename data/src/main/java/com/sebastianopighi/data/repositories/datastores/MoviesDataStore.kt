package com.sebastianopighi.data.repositories.datastores

import com.sebastianopighi.data.models.MovieData
import com.sebastianopighi.data.models.MovieDetailsData
import com.sebastianopighi.domain.models.ApiError

interface MoviesDataStore {
    fun getMovies(callback: (List<MovieData>?, ApiError?) -> Unit)
    fun getMovieDetails(movieId: Int, callback: (MovieDetailsData?, ApiError?) -> Unit)
    fun searchMovie(query: String, callback: (List<MovieData>?, ApiError?) -> Unit)
}