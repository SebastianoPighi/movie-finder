package com.sebastianopighi.data.models

import com.google.gson.annotations.SerializedName

data class MovieDetailsData(

        @SerializedName("id")
        var id: Int = -1,

        @SerializedName("title")
        var title: String,

        @SerializedName("poster_path")
        var posterPath: String? = null,

        @SerializedName("original_title")
        var originalTitle: String,

        @SerializedName("backdrop_path")
        var backdropPath: String? = null,

        @SerializedName("overview")
        var overview: String? = null
)