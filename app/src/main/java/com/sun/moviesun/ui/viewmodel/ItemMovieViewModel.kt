package com.sun.moviesun.ui.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sun.moviesun.BR
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.util.OnItemRecyclerViewClick
import com.sun.moviesun.util.extension.notNull

class ItemMovieViewModel(
    private val listener: OnItemRecyclerViewClick<Movie>? = null
) : BaseObservable() {

  @Bindable
  var movie: Movie? = null

  fun setData(data: Movie?) {
    data.notNull {
      movie = it
      notifyPropertyChanged(BR.movie)
    }
  }

  fun onItemClick() { movie.notNull { listener?.onItemClickListener(it) } }
}
