package com.sun.moviesun.data.source.remote.response

import com.sun.moviesun.data.model.Video

data class VideoResponse(
    val id: Int,
    val results: List<Video>
)