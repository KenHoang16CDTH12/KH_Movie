package com.sun.moviesun.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(protected val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

  abstract fun bindData(data: T)

  fun view() = binding.root
}
