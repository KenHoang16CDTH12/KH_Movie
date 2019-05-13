package com.sun.moviesun.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.sun.moviesun.data.model.Person

data class PersonResponse(
    val page: Int,
    val results: List<Person>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)