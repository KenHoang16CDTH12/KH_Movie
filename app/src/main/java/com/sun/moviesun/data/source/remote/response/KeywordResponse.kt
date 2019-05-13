package com.sun.moviesun.data.source.remote.response

import com.sun.moviesun.data.model.Keyword

data class KeywordResponse(
    val id: Int,
    val keywords: List<Keyword>
)