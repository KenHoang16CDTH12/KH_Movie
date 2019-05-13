package com.sun.moviesun.ui.home.favorite

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.data.repository.MovieRepository
import com.sun.moviesun.ui.home.genres.MovieGridAdapter
import com.sun.moviesun.util.OnItemRecyclerViewClick
import com.sun.moviesun.util.extension.notNull
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavoriteViewModel constructor(
    private val movieRepository: MovieRepository,
    private val navigator: FavoriteNavigator
) : BaseObservable(), OnItemRecyclerViewClick<Movie> {

  val adapterFavoriteObservable: ObservableField<MovieGridAdapter> = ObservableField()
  val compositeDisposable: CompositeDisposable = CompositeDisposable()

  init {
    setUpAdapter()
    loadData()
  }

  private fun setUpAdapter() {
    adapterFavoriteObservable.set(MovieGridAdapter(this))
  }

  fun loadData() {
    loadMovies()
  }

  fun loadMovies() {
    val disposable = movieRepository.getMoviesFavorite()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          adapterFavoriteObservable.get()!!.replaceItems(it)
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
}