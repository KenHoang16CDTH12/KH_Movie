package com.sun.moviesun.ui.detail.company

import com.sun.moviesun.data.model.entity.Movie

interface CompanyDetailNavigator {
  fun onClickItemMovie(movie: Movie)
}