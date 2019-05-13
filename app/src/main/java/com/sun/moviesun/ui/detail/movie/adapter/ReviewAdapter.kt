package com.sun.moviesun.ui.detail.movie.adapter

import androidx.databinding.ViewDataBinding
import com.sun.moviesun.R
import com.sun.moviesun.base.BaseAdapter
import com.sun.moviesun.base.BaseViewHolder
import com.sun.moviesun.data.model.Review
import com.sun.moviesun.databinding.ItemReviewBinding
import com.sun.moviesun.ui.viewmodel.ItemReviewViewModel
import com.sun.moviesun.util.OnItemRecyclerViewClick

class ReviewAdapter(
    private var onItemRecyclerViewClick: OnItemRecyclerViewClick<Review>? = null
) : BaseAdapter<Review>() {

  override fun layout(row: Int): Int = R.layout.item_review

  override fun viewHolder(binding: ViewDataBinding): BaseViewHolder<Review> = ReviewViewHolder(binding, onItemRecyclerViewClick!!)

  class ReviewViewHolder(
      binding: ViewDataBinding,
      private val listener: OnItemRecyclerViewClick<Review>
  ) : BaseViewHolder<Review>(binding) {

    override fun bindData(data: Review) {
      if (binding is ItemReviewBinding) {
        binding.viewModel = ItemReviewViewModel(listener)
        binding.viewModel!!.setData(data)
        binding.executePendingBindings()
      }
    }
  }
}
