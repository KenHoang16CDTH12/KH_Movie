package com.sun.moviesun.ui.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.sun.moviesun.data.model.Crew
import com.sun.moviesun.util.OnItemRecyclerViewClick
import com.sun.moviesun.util.extension.notNull

class ItemCrewViewModel(
    private val listener: OnItemRecyclerViewClick<Crew>? = null
) : BaseObservable() {

  @Bindable
  var crew: Crew? = null

  fun setData(data: Crew?) {
    data.notNull {
      crew = it
      notifyPropertyChanged(BR.crew)
    }
  }

  fun onItemClick() { crew.notNull { listener?.onItemClickListener(it) } }
}
