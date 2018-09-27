package com.sebastianopighi.moviefinder.models

data class Movie(
        var id: Int = 0,
        var title: String,
        var posterPath: String?,
        var originalTitle: String
)