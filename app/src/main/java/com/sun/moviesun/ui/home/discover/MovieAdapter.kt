package com.sun.moviesun.ui.home.discover

import androidx.databinding.ViewDataBinding
import com.sun.moviesun.R
import com.sun.moviesun.base.BaseAdapter
import com.sun.moviesun.base.BaseViewHolder
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.databinding.ItemMovieBinding
import com.sun.moviesun.ui.viewmodel.ItemMovieViewModel
import com.sun.moviesun.util.OnItemRecyclerViewClick

class MovieAdapter(
    private var onItemRecyclerViewClick: OnItemRecyclerViewClick<Movie>? = null
) : BaseAdapter<Movie>() {

  override fun layout(row: Int): Int = R.layout.item_movie

  override fun viewHolder(binding: ViewDataBinding): BaseViewHolder<Movie> = MovieViewHolder(binding, onItemRecyclerViewClick!!)

  class MovieViewHolder(
      binding: ViewDataBinding,
      private val listener: OnItemRecyclerViewClick<Movie>
  ) : BaseViewHolder<Movie>(binding) {

    override fun bindData(data: Movie) {
      if (binding is ItemMovieBinding) {
        binding.viewModel = ItemMovieViewModel(listener)
        binding.viewModel!!.setData(data)
        binding.executePendingBindings()
      }
    }
  }
}

