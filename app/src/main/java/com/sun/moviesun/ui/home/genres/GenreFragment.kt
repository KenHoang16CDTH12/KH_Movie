package com.sun.moviesun.ui.home.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sun.moviesun.R
import com.sun.moviesun.base.RecyclerViewPaginator
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.databinding.GenreFragmentBinding
import com.sun.moviesun.ui.detail.movie.MovieDetailActivity
import com.sun.moviesun.util.extension.provideMovieRepository

class GenreFragment : Fragment(), GenreNavigator {

  private lateinit var genreBinding: GenreFragmentBinding
  private lateinit var genreViewModel: GenreViewModel

  private var genreId = 0

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    arguments?.getInt(ARGUMENT_GENRE_ID)?.let {
      genreId = it
    }
    genreViewModel = GenreViewModel(genreId, activity!!.applicationContext.provideMovieRepository(), this)
    genreBinding = DataBindingUtil.inflate(inflater, R.layout.genre_fragment, container, false)
    initializeUI()
    return genreBinding.root
  }

  private fun initializeUI() {
    genreBinding.run {
      viewModel = genreViewModel
      RecyclerViewPaginator(
          recyclerView = recyclerMovies,
          loadMore = { page -> genreViewModel.loadMovies(page) }
      )
    }
  }

  override fun onStop() {
    super.onStop()
    genreViewModel.onCleared()
  }

  override fun onClickItemMovie(movie: Movie) {
    startActivity(MovieDetailActivity.newInstance(activity!!.applicationContext, movie))
  }

  companion object {
    private const val ARGUMENT_GENRE_ID = "com.sun.moviesun.ARGUMENT_GENRE_ID"
    fun newInstance(genreId: Int) = GenreFragment().apply {
      arguments = Bundle().apply {
        putInt(ARGUMENT_GENRE_ID, genreId)
      }
    }
  }
}
