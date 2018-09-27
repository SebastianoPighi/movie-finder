package com.sebastianopighi.moviefinder.models.mappers

import com.sebastianopighi.domain.models.MovieDetailsEntity
import com.sebastianopighi.domain.models.MovieEntity
import com.sebastianopighi.moviefinder.models.Movie
import com.sebastianopighi.moviefinder.models.MovieDetails

class MovieEntityToMovieMapper {

    companion object {
        const val posterBaseUrl = "https://image.tmdb.org/t/p/w342"
        const val backdropBaseUrl = "https://image.tmdb.org/t/p/w780"
    }

    fun mapFrom(from: MovieEntity): Movie {
        val movie = Movie(
                id = from.id,
                title = from.title,
                posterPath = from.posterPath?.let { posterBaseUrl + from.posterPath },
                originalTitle = from.originalTitle
        )

        return movie
    }

    fun mapFrom(from: MovieDetailsEntity?): MovieDetails? {
        from?.let {
            return MovieDetails(
                    id = from.id,
                    title = from.title,
                    posterPath = from.posterPath?.let { posterBaseUrl + from.posterPath },
                    originalTitle = from.originalTitle,
                    backdropPath = from.backdropPath?.let { backdropBaseUrl + from.backdropPath },
                    overview = from.overview
            )
        }
        return null
    }
}