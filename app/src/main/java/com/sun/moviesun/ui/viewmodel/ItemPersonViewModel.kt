package com.sun.moviesun.ui.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.sun.moviesun.data.model.Person
import com.sun.moviesun.util.OnItemRecyclerViewClick
import com.sun.moviesun.util.extension.notNull

class ItemPersonViewModel(
    private val listener: OnItemRecyclerViewClick<Person>? = null
) : BaseObservable() {

  @Bindable
  var person: Person? = null

  fun setData(data: Person?) {
    data.notNull {
      person = it
      notifyPropertyChanged(BR.person)
    }
  }

  fun onItemClick() { person.notNull { listener?.onItemClickListener(it) } }
}
