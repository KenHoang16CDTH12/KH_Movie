package com.sun.moviesun.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sun.moviesun.R
import com.sun.moviesun.base.RecyclerViewPaginator
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.databinding.SearchActivityBinding
import com.sun.moviesun.ui.detail.movie.MovieDetailActivity
import com.sun.moviesun.util.extension.provideMovieRepository
import kotlinx.android.synthetic.main.toolbar_search.*

class SearchActivity : AppCompatActivity(), SearchNavigator, TextWatcher {

  private lateinit var searchBinding: SearchActivityBinding
  private lateinit var searchViewModel: SearchViewModel
  private lateinit var paginator: RecyclerViewPaginator
  private var keyword = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    searchBinding = DataBindingUtil.setContentView(this, R.layout.search_activity)
    searchViewModel = SearchViewModel(applicationContext.provideMovieRepository(), this)
    initializeUI()
  }

  private fun initializeUI() {
    searchBinding.run {
      viewModel = searchViewModel
      paginator = RecyclerViewPaginator(
          recyclerView = recyclerSearch,
          loadMore = { page -> searchViewModel.searchMovie(keyword, page) }
      )
      imageBack.setOnClickListener { onBackPressed() }
      textInput.addTextChangedListener(this@SearchActivity)
    }
  }

  override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    paginator.resetCurrentPage()
    keyword = s.toString().trim()
    searchViewModel.searchMovie(keyword)
  }

  override fun afterTextChanged(editable: Editable?) {}

  override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

  override fun onClickItemMovie(movie: Movie) {
    startActivity(MovieDetailActivity.newInstance(applicationContext, movie))
  }

  override fun onStop() {
    super.onStop()
    searchViewModel.onCleared()
  }

  companion object {
    fun newInstance(context: Context) = Intent(context, SearchActivity::class.java)
  }
}
