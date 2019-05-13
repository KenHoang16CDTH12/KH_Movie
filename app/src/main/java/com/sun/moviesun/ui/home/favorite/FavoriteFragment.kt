package com.sun.moviesun.ui.home.favorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sun.moviesun.R
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.databinding.FavoriteFragmentBinding
import com.sun.moviesun.ui.detail.movie.MovieDetailActivity
import com.sun.moviesun.util.extension.provideMovieRepository

class FavoriteFragment : Fragment(), FavoriteNavigator {

  private lateinit var favoriteBinding: FavoriteFragmentBinding
  private lateinit var favoriteViewModel: FavoriteViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {

    favoriteViewModel = FavoriteViewModel(activity!!.applicationContext.provideMovieRepository(), this)
    favoriteBinding = DataBindingUtil.inflate(inflater, R.layout.favorite_fragment, container, false)
    initializeUI()
    return favoriteBinding.root
  }

  private fun initializeUI() {
    favoriteBinding.viewModel = favoriteViewModel
  }

  override fun onResume() {
    super.onResume()
  }

  override fun onStop() {
    super.onStop()
    favoriteViewModel.onCleared()
  }

  override fun onClickItemMovie(movie: Movie) {
    startActivity(MovieDetailActivity.newInstance(activity!!.applicationContext, movie))
  }

  companion object {
    fun newInstance() = FavoriteFragment()
  }
}
