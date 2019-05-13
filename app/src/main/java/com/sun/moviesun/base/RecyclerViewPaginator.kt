package com.sun.moviesun.base

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewPaginator(
    recyclerView: RecyclerView,
    private val loadMore: (Int) -> Unit
) : RecyclerView.OnScrollListener() {

  var threshold = DEFAULT_THRESH_HOLD
  var currentPage: Int = DEFAULT_PAGE
  var endWithAuto = false

  init { recyclerView.addOnScrollListener(this) }

  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    super.onScrolled(recyclerView, dx, dy)
    val layoutManager = recyclerView.layoutManager
    layoutManager?.let {
      val visibleItemCount = it.childCount
      val totalItemCount = it.itemCount
      val firstVisibleItemPosition = when (layoutManager) {
        is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
        is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
        else -> return
      }
      if (endWithAuto) {
        if (visibleItemCount + firstVisibleItemPosition == totalItemCount) return
      }
      if ((visibleItemCount + firstVisibleItemPosition + threshold) >= totalItemCount) {
        loadMore(++currentPage)
      }
    }
  }

  fun resetCurrentPage() { this.currentPage = DEFAULT_PAGE }

  companion object {
    private const val DEFAULT_PAGE = 1
    private const val DEFAULT_THRESH_HOLD = 10
  }
}