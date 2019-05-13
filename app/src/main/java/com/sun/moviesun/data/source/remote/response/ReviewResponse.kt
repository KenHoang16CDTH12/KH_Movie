package com.sun.moviesun.data.source.remote.response

import com.sun.moviesun.data.model.Review

class ReviewResponse(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val total_pages: Int,
    val total_results: Int
)