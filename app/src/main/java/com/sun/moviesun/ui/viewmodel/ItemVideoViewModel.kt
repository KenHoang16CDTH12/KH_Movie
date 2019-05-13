package com.sun.moviesun.ui.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.sun.moviesun.data.model.Video
import com.sun.moviesun.util.OnItemRecyclerViewClick
import com.sun.moviesun.util.extension.notNull

class ItemVideoViewModel(
    private val listener: OnItemRecyclerViewClick<Video>? = null
) : BaseObservable() {

  @Bindable
  var video: Video? = null

  fun setData(data: Video?) {
    data.notNull {
      video = it
      notifyPropertyChanged(BR.video)
    }
  }

  fun onItemClick() { video.notNull { listener?.onItemClickListener(it) } }
}
