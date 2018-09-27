package com.sebastianopighi.domain.models

data class MovieDetailsEntity(
        var id: Int = -1,

        var title: String,

        var posterPath: String? = null,

        var originalTitle: String,

        var backdropPath: String? = null,

        var overview: String? = null
)