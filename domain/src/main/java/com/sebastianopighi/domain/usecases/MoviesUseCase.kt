package com.sebastianopighi.domain.usecases

import com.sebastianopighi.domain.models.ApiError
import com.sebastianopighi.domain.models.MovieEntity
import com.sebastianopighi.domain.repositories.MoviesRepository

class MoviesUseCase(private val repository: MoviesRepository) {
    fun getMovies(moviesPage: Int, callback: (List<MovieEntity>?, ApiError?) -> Unit) = repository.getRepoMovies(moviesPage, callback)
    fun searchMovie(movieTitleQuery: String, callback: (List<MovieEntity>?, ApiError?) -> Unit) = repository.searchRepoMovie(movieTitleQuery, callback)
}