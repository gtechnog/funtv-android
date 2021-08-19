package com.gtechnofy.funtv.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DiscoverMovies(
    @Json(name = "results")
    val movies: List<Movie>,

    @Json(name = "page")
    val page: Int
)

@JsonClass(generateAdapter = true)
data class Movie(
    val id: Int,
    val title: String,

    @Json(name = "backdrop_path")
    val imageUrl : String
)