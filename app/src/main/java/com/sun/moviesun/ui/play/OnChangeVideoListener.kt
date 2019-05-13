package com.sun.moviesun.ui.play

interface OnChangeVideoListener {
  fun setVideoKey(videoKey: String)
  fun playVideo()
  fun isPlaying(): Boolean
}