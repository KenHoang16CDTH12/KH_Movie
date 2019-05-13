package com.sun.moviesun.ui.home.discover

import com.sun.moviesun.data.model.entity.Movie

interface DiscoverNavigator {
  fun onClickItemMovie(movie: Movie)
}
