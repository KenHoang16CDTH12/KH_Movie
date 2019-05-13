package com.sun.moviesun.ui.play

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sun.moviesun.R
import com.sun.moviesun.data.model.Video
import com.sun.moviesun.databinding.PlayTrailerActivityBinding
import com.sun.moviesun.util.extension.simpleToolbarWithHome
import kotlinx.android.synthetic.main.play_trailer_activity.*

class PlayTrailerActivity : AppCompatActivity(), OnChangeVideoListener {

  private lateinit var playTrailerBinding: PlayTrailerActivityBinding
  private lateinit var playTrailerViewModel: PlayTrailerViewModel
  private lateinit var youtubePlayer: VideoFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    playTrailerViewModel = PlayTrailerViewModel()
    playTrailerViewModel.setVideo(getVideoFromIntent())
    playTrailerBinding = DataBindingUtil.setContentView(this, R.layout.play_trailer_activity)
    initializeUI()
    playTrailerViewModel.setOnChangeVideoListener(this)
    playTrailerViewModel.loadData()
  }

  private fun initializeUI() {
    simpleToolbarWithHome(toolbar, playTrailerViewModel.getVideo().name!!)
    playTrailerBinding.run {
      viewModel = playTrailerViewModel
      youtubePlayer = fragmentTrailer as VideoFragment
    }
  }

  private fun getVideoFromIntent(): Video {
    return intent.getParcelableExtra(EXTRA_VIDEO) as Video
  }

  override fun onStop() {
    super.onStop()
    playTrailerViewModel.onCleared()
  }

  override fun setVideoKey(videoKey: String) { youtubePlayer.setVideoKey(videoKey) }

  override fun playVideo() { youtubePlayer.playVideo() }

  override fun isPlaying(): Boolean = youtubePlayer.isPlaying()

  companion object {

    private const val EXTRA_VIDEO = "com.sun.moviesun.EXTRA_VIDEO"

    fun newInstance(context: Context, video: Video): Intent =
        Intent(context, PlayTrailerActivity::class.java).apply {
          putExtra(EXTRA_VIDEO, video)
        }
  }
}
