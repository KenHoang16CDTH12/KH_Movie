package com.sun.moviesun.ui.play

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.databinding.library.baseAdapters.BR
import com.sun.moviesun.data.model.Video
import io.reactivex.disposables.CompositeDisposable

class PlayTrailerViewModel : BaseObservable() {

  private var isPlaying: ObservableBoolean? = null
  private var listener: OnChangeVideoListener? = null
  private val compositeDisposable = CompositeDisposable()
  private lateinit var video: Video

  init {
    isPlaying = ObservableBoolean()
  }

  fun setVideo(video: Video) {
    this.video = video
    notifyPropertyChanged(BR.video)
  }

  @Bindable
  fun getVideo() = video

  fun loadData() {
    listener!!.setVideoKey(video.key!!)
    isPlaying!!.set(listener!!.isPlaying())
  }

  fun setOnChangeVideoListener(listener: OnChangeVideoListener) {
    this.listener = listener
  }

  fun handleError(t: Throwable) {
  }

  fun onCleared() {
    compositeDisposable.clear()
  }
}