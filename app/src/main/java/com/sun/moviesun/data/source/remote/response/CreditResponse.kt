package com.sun.moviesun.data.source.remote.response

import com.sun.moviesun.data.model.Cast
import com.sun.moviesun.data.model.Crew

data class CreditResponse(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>
)