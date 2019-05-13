package com.sun.moviesun.ui.detail.movie

import com.sun.moviesun.data.model.*

interface MovieDetailNavigator {
  fun onClickItemVideo(video: Video)
  fun onClickItemReview(review: Review)
  fun onClickItemCrew(crew: Crew)
  fun onClickItemCast(cast: Cast)
  fun onClickItemCompany(company: Company)
  fun notifySnackbar(resource: Int)
}