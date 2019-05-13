package com.sun.moviesun.ui.home.genres

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.data.repository.MovieRepository
import com.sun.moviesun.util.OnItemRecyclerViewClick
import com.sun.moviesun.util.extension.notNull
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GenreViewModel constructor(
    private val genreId: Int,
    private val movieRepository: MovieRepository,
    private val navigator: GenreNavigator
) : BaseObservable(), OnItemRecyclerViewClick<Movie> {

  val adapterMovieObservable: ObservableField<MovieGridAdapter> = ObservableField()
  val compositeDisposable: CompositeDisposable = CompositeDisposable()

  init {
    setUpAdapter()
    loadData()
  }

  private fun setUpAdapter() {
    adapterMovieObservable.set(MovieGridAdapter(this))
  }

  fun loadData() {
    loadMovies()
  }

  fun loadMovies(page: Int = DEFAULT_PAGE) {
    val disposable = movieRepository.getMoviesByGenre(genreId, page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          if (page == DEFAULT_PAGE)
            adapterMovieObservable.get()!!.replaceItems(it.results)
          else
            adapterMovieObservable.get()!!.addItems(it.results)
        }, { throwable -> handleError(throwable) })
    compositeDisposable.add(disposable)
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