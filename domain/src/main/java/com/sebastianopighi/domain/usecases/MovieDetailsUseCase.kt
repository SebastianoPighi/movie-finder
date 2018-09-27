package com.sebastianopighi.domain.usecases

import com.sebastianopighi.domain.models.ApiError
import com.sebastianopighi.domain.models.MovieDetailsEntity
import com.sebastianopighi.domain.repositories.MoviesRepository

class MovieDetailsUseCase(private val repository: MoviesRepository) {
    fun getMovieDetails(moviedId: Int, callback: (MovieDetailsEntity?, ApiError?) -> Unit) = repository.getRepoMovieDetails(moviedId, callback)
}