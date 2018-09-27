package com.sebastianopighi.moviefinder.models

data class MovieDetails(
        var id: Int = 0,
        var title: String,
        var posterPath: String?,
        var originalTitle: String,
        var backdropPath: String? = null,
        var overview: String? = null
)