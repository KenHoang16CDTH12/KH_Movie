package com.sun.moviesun.ui.detail.actor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.sun.moviesun.R
import com.sun.moviesun.base.RecyclerViewPaginator
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.databinding.ActorDetailActivityBinding
import com.sun.moviesun.ui.home.discover.MovieAdapter
import com.sun.moviesun.ui.detail.movie.MovieDetailActivity
import com.sun.moviesun.util.extension.provideMovieRepository
import com.sun.moviesun.util.extension.simpleToolbarWithHome
import kotlinx.android.synthetic.main.actor_detail_activity.*
import kotlinx.android.synthetic.main.layout_actor_detail_body.*

class ActorDetailActivity : AppCompatActivity(), ActorDetailNavigator {

  private lateinit var actorDetailBinding: ActorDetailActivityBinding
  private lateinit var actorDetailViewModel: ActorDetailViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    actorDetailViewModel = ActorDetailViewModel(applicationContext.provideMovieRepository(), this)
    actorDetailViewModel.setPeopleId(getPersonFromIntent())
    actorDetailViewModel.loadData()
    actorDetailBinding = DataBindingUtil.setContentView(this, R.layout.actor_detail_activity)
    initializeUI()
  }

  private fun initializeUI() {
    simpleToolbarWithHome(toolbar, getString(R.string.text_information))
    actorDetailBinding.run {
      viewModel = actorDetailViewModel
      RecyclerViewPaginator(
          recyclerView = recyclerMovies,
          loadMore = { page -> actorDetailViewModel.loadMovies(page) }
      )
    }
  }

  override fun onStop() {
    super.onStop()
    actorDetailViewModel.onCleared()
  }

  private fun getPersonFromIntent(): Int = intent.getIntExtra(EXTRA_PEOPLE_ID, 0)

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) onBackPressed()
    return false
  }

  override fun onClickItemMovie(movie: Movie) {
    startActivity(MovieDetailActivity.newInstance(applicationContext, movie))
  }

  companion object {

    private const val EXTRA_PEOPLE_ID = "com.sun.moviesun.EXTRA_PEOPLE_ID"

    fun newInstance(context: Context, peopleId: Int): Intent =
        Intent(context, ActorDetailActivity::class.java).apply {
          putExtra(EXTRA_PEOPLE_ID, peopleId)
        }
  }
}
