package com.sun.moviesun.ui.detail.movie.adapter

import androidx.databinding.ViewDataBinding
import com.sun.moviesun.R
import com.sun.moviesun.base.BaseAdapter
import com.sun.moviesun.base.BaseViewHolder
import com.sun.moviesun.data.model.Crew
import com.sun.moviesun.databinding.ItemCrewBinding
import com.sun.moviesun.ui.viewmodel.ItemCrewViewModel
import com.sun.moviesun.util.OnItemRecyclerViewClick

class CrewAdapter(
    private var onItemRecyclerViewClick: OnItemRecyclerViewClick<Crew>? = null
) : BaseAdapter<Crew>() {

  override fun layout(row: Int): Int = R.layout.item_crew

  override fun viewHolder(binding: ViewDataBinding): BaseViewHolder<Crew> = CrewViewHolder(binding, onItemRecyclerViewClick!!)

  class CrewViewHolder(
      binding: ViewDataBinding,
      private val listener: OnItemRecyclerViewClick<Crew>
  ) : BaseViewHolder<Crew>(binding) {

    override fun bindData(data: Crew) {
      if (binding is ItemCrewBinding) {
        binding.viewModel = ItemCrewViewModel(listener)
        binding.viewModel!!.setData(data)
        binding.executePendingBindings()
      }
    }
  }
}