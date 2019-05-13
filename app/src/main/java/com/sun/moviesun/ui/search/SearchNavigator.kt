package com.sun.moviesun.ui.search

import com.sun.moviesun.data.model.entity.Movie

interface SearchNavigator {
  fun onClickItemMovie(movie: Movie)
}