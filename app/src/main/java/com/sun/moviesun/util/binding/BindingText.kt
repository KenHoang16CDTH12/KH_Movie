package com.sun.moviesun.util.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingText {

  @JvmStatic
  @BindingAdapter("bindTotalResults")
  fun bindTotalResults(textView: TextView, size: Int) {
    textView.text = StringBuilder("Movies (").append(size).append(")").toString()
  }
}