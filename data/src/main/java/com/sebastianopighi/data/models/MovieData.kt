package com.sebastianopighi.data.models

import com.google.gson.annotations.SerializedName

data class MovieData(

        @SerializedName("id")
        var id: Int = -1,

        @SerializedName("title")
        var title: String,

        @SerializedName("poster_path")
        var posterPath: String? = null,

        @SerializedName("original_title")
        var originalTitle: String
)