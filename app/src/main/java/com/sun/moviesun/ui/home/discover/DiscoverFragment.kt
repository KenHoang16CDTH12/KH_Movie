package com.sun.moviesun.ui.home.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.sun.moviesun.R
import com.sun.moviesun.base.RecyclerViewPaginator
import com.sun.moviesun.data.annotation.CategoryKeyDef
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.databinding.DiscoverFragmentBinding
import com.sun.moviesun.ui.detail.movie.MovieDetailActivity
import com.sun.moviesun.util.extension.provideMovieRepository

class DiscoverFragment : Fragment(), ViewPager.OnPageChangeListener, DiscoverNavigator {

  private lateinit var discoverBinding: DiscoverFragmentBinding
  private lateinit var discoverViewModel: DiscoverViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    discoverViewModel = DiscoverViewModel(activity!!.applicationContext.provideMovieRepository(), this)
    discoverBinding = DataBindingUtil.inflate(inflater, R.layout.discover_fragment, container, false)
    initializeUI()
    return discoverBinding.root
  }

  private fun initializeUI() {
    discoverBinding.run {

      viewModel = discoverViewModel
      indicator.setupWithViewPager(pagerSlider, true)
      pagerSlider.addOnPageChangeListener(this@DiscoverFragment)
      RecyclerViewPaginator(
          recyclerView = recyclerNowPlayingMovies,
          loadMore = { page -> discoverViewModel.loadMovies(CategoryKeyDef.NOW_PLAYING, page) }
      )
      RecyclerViewPaginator(
          recyclerView = recyclerPopularMovies,
          loadMore = { page -> discoverViewModel.loadMovies(CategoryKeyDef.POPULAR, page) }
      )
      RecyclerViewPaginator(
          recyclerView = recyclerTopRateMovies,
          loadMore = { page -> discoverViewModel.loadMovies(CategoryKeyDef.TOP_RATED, page) }
      )
      RecyclerViewPaginator(
          recyclerView = recyclerUpComingMovies,
          loadMore = { page -> discoverViewModel.loadMovies(CategoryKeyDef.UPCOMING, page) }
      )
    }
  }

  override fun onStop() {
    super.onStop()
    discoverViewModel.onCleared()
  }

  override fun onClickItemMovie(movie: Movie) {
    startActivity(MovieDetailActivity.newInstance(activity!!.applicationContext, movie))
  }

  override fun onPageScrollStateChanged(state: Int) {
  }

  override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
  }

  override fun onPageSelected(position: Int) {
    SliderAdapter.setCurrentPosition(position)
  }

  companion object {
    fun newInstance() = DiscoverFragment()
  }
}
