package com.carce.moviewer.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("title")
    val title: String,
    @SerializedName("backdrop_path")
    val image: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("vote_average")
    val voteAverage: String
)