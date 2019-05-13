package com.sun.moviesun.ui.home.actor

import androidx.databinding.ViewDataBinding
import com.sun.moviesun.R
import com.sun.moviesun.base.BaseAdapter
import com.sun.moviesun.base.BaseViewHolder
import com.sun.moviesun.data.model.Person
import com.sun.moviesun.databinding.ItemActorGridBinding
import com.sun.moviesun.ui.viewmodel.ItemPersonViewModel
import com.sun.moviesun.util.OnItemRecyclerViewClick

class ActorGridAdapter(
    private var onItemRecyclerViewClick: OnItemRecyclerViewClick<Person>? = null
) : BaseAdapter<Person>() {

  override fun layout(row: Int): Int = R.layout.item_actor_grid

  override fun viewHolder(binding: ViewDataBinding): BaseViewHolder<Person> = ActorViewHolder(binding, onItemRecyclerViewClick!!)

  class  ActorViewHolder(
      binding: ViewDataBinding,
      private val listener: OnItemRecyclerViewClick<Person>
  ) : BaseViewHolder<Person>(binding) {

    override fun bindData(data: Person) {
      if (binding is ItemActorGridBinding) {
        binding.viewModel = ItemPersonViewModel(listener)
        binding.viewModel!!.setData(data)
        binding.executePendingBindings()
      }
    }
  }
}