package com.sun.moviesun.ui.detail.actor

import com.sun.moviesun.data.model.entity.Movie

interface ActorDetailNavigator {
  fun onClickItemMovie(movie: Movie)
}