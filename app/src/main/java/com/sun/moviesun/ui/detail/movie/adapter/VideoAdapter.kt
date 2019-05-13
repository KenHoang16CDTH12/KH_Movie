package com.sun.moviesun.ui.detail.movie.adapter

import androidx.databinding.ViewDataBinding
import com.sun.moviesun.R
import com.sun.moviesun.base.BaseAdapter
import com.sun.moviesun.base.BaseViewHolder
import com.sun.moviesun.data.model.Video
import com.sun.moviesun.databinding.ItemTrailerBinding
import com.sun.moviesun.ui.viewmodel.ItemVideoViewModel
import com.sun.moviesun.util.OnItemRecyclerViewClick

class VideoAdapter(
    private var onItemRecyclerViewClick: OnItemRecyclerViewClick<Video>? = null
) : BaseAdapter<Video>() {

  override fun layout(row: Int): Int = R.layout.item_trailer

  override fun viewHolder(binding: ViewDataBinding): BaseViewHolder<Video> = VideoViewHolder(binding, onItemRecyclerViewClick!!)

  class VideoViewHolder(
      binding: ViewDataBinding,
      private val listener: OnItemRecyclerViewClick<Video>
  ) : BaseViewHolder<Video>(binding) {

    override fun bindData(data: Video) {
      if (binding is ItemTrailerBinding) {
        binding.viewModel = ItemVideoViewModel(listener)
        binding.viewModel!!.setData(data)
        binding.executePendingBindings()
      }
    }
  }
}