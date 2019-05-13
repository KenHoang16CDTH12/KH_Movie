package com.sun.moviesun.ui.home.favorite

import com.sun.moviesun.data.model.entity.Movie

interface FavoriteNavigator {
  fun onClickItemMovie(movie: Movie)
}
