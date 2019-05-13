package com.sun.moviesun.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.sun.moviesun.data.model.entity.Movie

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)
