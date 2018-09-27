package com.sebastianopighi.domain.models

data class MovieEntity(
        var id: Int = 0,
        var title: String,
        var posterPath: String?,
        var originalTitle: String
)