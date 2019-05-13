package com.sun.moviesun.util.binding

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import com.sun.moviesun.BuildConfig
import com.sun.moviesun.custom.layout.tagview.TagContainerLayout
import com.sun.moviesun.data.model.Keyword
import com.sun.moviesun.ui.home.discover.SliderAdapter
import java.util.*

object BindingUtils {

  private const val FIRST_INDEX = 0
  private const val DELAY = 5000L
  private const val PLUS = 1
  private const val DURATION = 5000L

  @JvmStatic
  @BindingAdapter("bindTags")
  fun bindTags(tagContainer: TagContainerLayout?, keywords: List<Keyword>) {
    var list: MutableList<String> = ArrayList()
    for (keyword in keywords) {
      keyword.name?.let { list.add(it) }
    }
    if (list.size > 7) {
      list = list.subList(0, 6)
    }
    tagContainer!!.tags = list
  }

  @JvmStatic
  @BindingAdapter("bindTags")
  fun bindTagStrings(tagContainer: TagContainerLayout?, list: List<String>?) {
    list?.let { tagContainer!!.tags = it }
  }

  @JvmStatic
  @BindingAdapter("bindAutoSwitchSlide")
  fun bindAutoSwitchSlide(pager: ViewPager, isAuto: Boolean = false) {
    val timerTask = object : TimerTask() {
      override fun run() {
        pager.post(Runnable {
          if (pager.currentItem > pager.childCount + PLUS) {
            pager.currentItem = FIRST_INDEX
            SliderAdapter.setCurrentPosition(pager.currentItem)
            return@Runnable
          }
          pager.currentItem = pager.currentItem + PLUS
          SliderAdapter.setCurrentPosition(pager.currentItem)
        })
      }
    }
    if (isAuto) {
      val timer = Timer()
      timer.schedule(timerTask, DELAY, DURATION)
    }
  }

  @JvmStatic
  @BindingAdapter("bindThumbnailYoutube")
  fun bindThumbnailYoutube(thumbnail: YouTubeThumbnailView, key: String) {
    val listener = object : YouTubeThumbnailView.OnInitializedListener {
      override fun onInitializationSuccess(view: YouTubeThumbnailView, loader: YouTubeThumbnailLoader) {
        loader.setVideo(key)
        loader.setOnThumbnailLoadedListener(
            object : YouTubeThumbnailLoader.OnThumbnailLoadedListener {
              override fun onThumbnailLoaded(youTubeThumbnailView: YouTubeThumbnailView, s: String) {
                loader.release()
              }

              override fun onThumbnailError(view: YouTubeThumbnailView, error: YouTubeThumbnailLoader.ErrorReason) {}
            })
      }

      override fun onInitializationFailure(view: YouTubeThumbnailView, result: YouTubeInitializationResult) {}
    }
    thumbnail.initialize(BuildConfig.YOUTUBE_API_KEY, listener)
  }
}
