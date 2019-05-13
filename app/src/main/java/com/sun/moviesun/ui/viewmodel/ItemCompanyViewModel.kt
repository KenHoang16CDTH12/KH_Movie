package com.sun.moviesun.ui.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.sun.moviesun.data.model.Company
import com.sun.moviesun.util.OnItemRecyclerViewClick
import com.sun.moviesun.util.extension.notNull

class ItemCompanyViewModel(
    private val listener: OnItemRecyclerViewClick<Company>? = null
) : BaseObservable() {

  @Bindable
  var company: Company? = null

  fun setData(data: Company?) {
    data.notNull {
      company = it
      notifyPropertyChanged(BR.company)
    }
  }

  fun onItemClick() { company.notNull { listener?.onItemClickListener(it) } }
}
