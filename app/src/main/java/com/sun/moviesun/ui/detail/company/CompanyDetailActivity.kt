package com.sun.moviesun.ui.detail.company

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.sun.moviesun.R
import com.sun.moviesun.base.RecyclerViewPaginator
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.databinding.CompanyDetailActivityBinding
import com.sun.moviesun.ui.detail.movie.MovieDetailActivity
import com.sun.moviesun.ui.home.discover.MovieAdapter
import com.sun.moviesun.util.extension.provideMovieRepository
import com.sun.moviesun.util.extension.simpleToolbarWithHome
import kotlinx.android.synthetic.main.company_detail_activity.*
import kotlinx.android.synthetic.main.layout_company_detail_body.*

class CompanyDetailActivity : AppCompatActivity(), CompanyDetailNavigator {

  private lateinit var companyDetailBinding: CompanyDetailActivityBinding
  private lateinit var companyDetailViewModel: CompanyDetailViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    companyDetailViewModel = CompanyDetailViewModel(applicationContext.provideMovieRepository(), this)
    companyDetailViewModel.setCompanyId(getCompanyFromIntent())
    companyDetailViewModel.loadData()
    companyDetailBinding = DataBindingUtil.setContentView(this, R.layout.company_detail_activity)
    initializeUI()
  }

  private fun initializeUI() {
    simpleToolbarWithHome(toolbar, getString(R.string.text_company_information))
    companyDetailBinding.run {
      viewModel = companyDetailViewModel
      RecyclerViewPaginator(
          recyclerView = recyclerMovies,
          loadMore = { page -> companyDetailViewModel.loadMovies(page) }
      )
    }
  }

  override fun onStop() {
    super.onStop()
    companyDetailViewModel.onCleared()
  }

  private fun getCompanyFromIntent(): Int = intent.getIntExtra(EXTRA_COMPANY_ID, 0)

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) onBackPressed()
    return false
  }

  override fun onClickItemMovie(movie: Movie) {
    startActivity(MovieDetailActivity.newInstance(applicationContext, movie))
  }

  companion object {

    private const val EXTRA_COMPANY_ID = "com.sun.moviesun.EXTRA_COMPANY_ID"

    fun newInstance(context: Context, companyId: Int): Intent =
        Intent(context, CompanyDetailActivity::class.java).apply {
          putExtra(EXTRA_COMPANY_ID, companyId)
        }
  }
}
