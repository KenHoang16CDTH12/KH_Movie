package com.sun.moviesun.ui.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.sun.moviesun.data.model.Cast
import com.sun.moviesun.util.OnItemRecyclerViewClick
import com.sun.moviesun.util.extension.notNull

class ItemCastViewModel(
    private val listener: OnItemRecyclerViewClick<Cast>? = null
) : BaseObservable() {

  @Bindable
  var cast: Cast? = null

  fun setData(data: Cast?) {
    data.notNull {
      cast = it
      notifyPropertyChanged(BR.cast)
    }
  }

  fun onItemClick() { cast.notNull { listener?.onItemClickListener(it) } }
}

