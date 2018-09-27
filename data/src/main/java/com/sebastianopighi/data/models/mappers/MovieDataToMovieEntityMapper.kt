package com.sebastianopighi.data.models.mappers

import com.sebastianopighi.data.models.MovieData
import com.sebastianopighi.data.models.MovieDetailsData
import com.sebastianopighi.domain.models.MovieDetailsEntity
import com.sebastianopighi.domain.models.MovieEntity

class MovieDataToMovieEntityMapper {

    fun mapFrom(from: MovieData): MovieEntity {
        return MovieEntity(
                id = from.id,
                title = from.title,
                posterPath = from.posterPath,
                originalTitle = from.originalTitle
        )
    }

    fun mapFrom(from: MovieDetailsData?): MovieDetailsEntity? {
        from?.let {
            return MovieDetailsEntity(
                    id = from.id,
                    title = from.title,
                    posterPath = from.posterPath,
                    originalTitle = from.originalTitle,
                    backdropPath = from.backdropPath,
                    overview = from.overview
            )
        }
        return null
    }
}