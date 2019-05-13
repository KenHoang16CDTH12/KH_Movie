package com.sun.moviesun.ui.detail.movie.adapter

import androidx.databinding.ViewDataBinding
import com.sun.moviesun.R
import com.sun.moviesun.base.BaseAdapter
import com.sun.moviesun.base.BaseViewHolder
import com.sun.moviesun.data.model.Company
import com.sun.moviesun.databinding.ItemCompanyBinding
import com.sun.moviesun.ui.viewmodel.ItemCompanyViewModel
import com.sun.moviesun.util.OnItemRecyclerViewClick

class CompanyAdapter(
    private var onItemRecyclerViewClick: OnItemRecyclerViewClick<Company>? = null
) : BaseAdapter<Company>() {

  override fun layout(row: Int): Int = R.layout.item_company

  override fun viewHolder(binding: ViewDataBinding): BaseViewHolder<Company> = CompanyViewHolder(binding, onItemRecyclerViewClick!!)

  class CompanyViewHolder(
      binding: ViewDataBinding,
      private val listener: OnItemRecyclerViewClick<Company>
  ) : BaseViewHolder<Company>(binding) {

    override fun bindData(data: Company) {
      if (binding is ItemCompanyBinding) {
        binding.viewModel = ItemCompanyViewModel(listener)
        binding.viewModel!!.setData(data)
        binding.executePendingBindings()
      }
    }
  }
}