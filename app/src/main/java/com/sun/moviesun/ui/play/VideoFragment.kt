package com.sun.moviesun.ui.play

import android.os.Bundle
import android.util.Log
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.sun.moviesun.BuildConfig

class VideoFragment : YouTubePlayerSupportFragment(), YouTubePlayer.OnInitializedListener {

  private lateinit var youTubePlayer: YouTubePlayer
  private var videoKey: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initialize(BuildConfig.YOUTUBE_API_KEY, this)
  }

  override fun onDestroy() {
    super.onDestroy()
    youTubePlayer.release()
  }

  override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, restored: Boolean) {
    youTubePlayer = player!!
    youTubePlayer.loadVideo(videoKey)
    Log.e("AAA", "Initialize success")
  }

  override fun onInitializationFailure(provider: YouTubePlayer.Provider?, result: YouTubeInitializationResult?) {
  }

  fun setVideoKey(videoKey: String) {
    this.videoKey = videoKey
    youTubePlayer.cueVideo(videoKey)
  }

  fun playVideo() { youTubePlayer.play() }

  fun isPlaying(): Boolean = youTubePlayer.isPlaying

  fun getVideoId(): String = videoKey!!

  fun pause() { youTubePlayer.pause() }
}