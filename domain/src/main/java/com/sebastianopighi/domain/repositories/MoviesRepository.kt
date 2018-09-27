package com.sebastianopighi.domain.repositories

import com.sebastianopighi.domain.models.ApiError
import com.sebastianopighi.domain.models.MovieDetailsEntity
import com.sebastianopighi.domain.models.MovieEntity

interface MoviesRepository {
    fun getRepoMovies(moviesPage: Int, callback: (List<MovieEntity>?, ApiError?) -> Unit)
    fun getRepoMovieDetails(moviedId: Int, callback: (MovieDetailsEntity?, ApiError?) -> Unit)
    fun searchRepoMovie(movieTitleQuery: String, callback: (List<MovieEntity>?, ApiError?) -> Unit)
}