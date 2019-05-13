package com.sun.moviesun.ui.search

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.data.repository.MovieRepository
import com.sun.moviesun.ui.home.genres.MovieGridAdapter
import com.sun.moviesun.util.OnItemRecyclerViewClick
import com.sun.moviesun.util.extension.notNull
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class SearchViewModel constructor(
    private val movieRepository: MovieRepository,
    private val navigator: SearchNavigator
) : BaseObservable(), OnItemRecyclerViewClick<Movie> {

  val totalResults: ObservableInt = ObservableInt(0)
  val adapterMovieObservable: ObservableField<MovieGridAdapter> = ObservableField()
  val compositeDisposable: CompositeDisposable = CompositeDisposable()

  init {
    setUpAdapter()
  }

  private fun setUpAdapter() {
    adapterMovieObservable.set(MovieGridAdapter(this))
  }

  fun searchMovie(keyword: String, page: Int = DEFAULT_PAGE) {
    if (keyword.isEmpty()) {
      clearSearch()
      return
    }
    val disposable = movieRepository.searchMovie(keyword, page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          if (page == DEFAULT_PAGE)
            adapterMovieObservable.get()!!.replaceItems(it.results)
          else
            adapterMovieObservable.get()!!.addItems(it.results)
          totalResults.set(it!!.totalResults)
        }, { throwable -> handleError(throwable) })
    compositeDisposable.add(disposable)
  }

  fun clearSearch() {
    adapterMovieObservable.get()!!.clearList()
    totalResults.set(0)
  }

  fun handleError(t: Throwable) {
  }

  fun onCleared() {
    compositeDisposable.clear()
  }

  override fun onItemClickListener(data: Movie) {
    navigator.notNull { it.onClickItemMovie(data) }
  }

  companion object {
    private const val DEFAULT_PAGE = 1
  }
}