package com.sun.moviesun.ui.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.sun.moviesun.data.model.Review
import com.sun.moviesun.util.OnItemRecyclerViewClick
import com.sun.moviesun.util.extension.notNull

class ItemReviewViewModel(
    private val listener: OnItemRecyclerViewClick<Review>? = null
) : BaseObservable() {

  @Bindable
  var review: Review? = null

  fun setData(data: Review?) {
    data.notNull {
      review = it
      notifyPropertyChanged(BR.review)
    }
  }

  fun onItemClick() { review.notNull { listener?.onItemClickListener(it) } }
}

