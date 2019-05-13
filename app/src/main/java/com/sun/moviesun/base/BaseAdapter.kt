package com.sun.moviesun.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

  private var data = mutableListOf<T>()

  fun replaceItems(items: List<T>) {
    data.clear()
    data.addAll(ArrayList(items))
    notifyDataSetChanged()
  }

  fun addItems(items: List<T>) {
    val tagPosition = data.size
    data.addAll(ArrayList(items))
    notifyItemRangeChanged(tagPosition, items.size)
  }

  fun addItem(item: T) {
    data.add(item)
    notifyItemInserted(data.indexOf(item))
  }

  fun clearList() {
    data.clear()
    notifyDataSetChanged()
  }

  protected abstract fun layout(row: Int): Int

  protected abstract fun viewHolder(binding: ViewDataBinding): BaseViewHolder<T>

  override fun onCreateViewHolder(viewGroup: ViewGroup, @LayoutRes layout: Int): BaseViewHolder<T> {
    val binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), layout, viewGroup, false) as ViewDataBinding
    return viewHolder(binding)
  }

  override fun onBindViewHolder(viewHolder: BaseViewHolder<T>, position: Int) {
    viewHolder.bindData(objectFromPosition(position))
  }

  override fun getItemViewType(position: Int): Int = layout(position)

  private fun objectFromPosition(position: Int) = data[position]!!

  override fun getItemCount() = data.size
}
