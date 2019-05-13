package com.sun.moviesun.ui.home.genres

import com.sun.moviesun.data.model.entity.Movie

interface GenreNavigator {
  fun onClickItemMovie(movie: Movie)
}