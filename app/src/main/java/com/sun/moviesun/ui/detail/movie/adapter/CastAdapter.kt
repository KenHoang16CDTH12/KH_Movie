package com.sun.moviesun.ui.detail.movie.adapter

import androidx.databinding.ViewDataBinding
import com.sun.moviesun.R
import com.sun.moviesun.base.BaseAdapter
import com.sun.moviesun.base.BaseViewHolder
import com.sun.moviesun.data.model.Cast
import com.sun.moviesun.databinding.ItemCastBinding
import com.sun.moviesun.ui.viewmodel.ItemCastViewModel
import com.sun.moviesun.util.OnItemRecyclerViewClick

class CastAdapter(
    private var onItemRecyclerViewClick: OnItemRecyclerViewClick<Cast>? = null
) : BaseAdapter<Cast>() {

  override fun layout(row: Int): Int = R.layout.item_cast

  override fun viewHolder(binding: ViewDataBinding): BaseViewHolder<Cast> = CastViewHolder(binding, onItemRecyclerViewClick!!)

  class CastViewHolder(
      binding: ViewDataBinding,
      private val listener: OnItemRecyclerViewClick<Cast>
  ) : BaseViewHolder<Cast>(binding) {

    override fun bindData(data: Cast) {
      if (binding is ItemCastBinding) {
        binding.viewModel = ItemCastViewModel(listener)
        binding.viewModel!!.setData(data)
        binding.executePendingBindings()
      }
    }
  }
}