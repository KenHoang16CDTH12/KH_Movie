package com.sun.moviesun.ui.home.genres

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.fragment.app.FragmentManager
import com.sun.moviesun.data.model.Genre
import com.sun.moviesun.data.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GenresViewModel constructor(
    private val movieRepository: MovieRepository
) : BaseObservable() {

  val genresObservable: ObservableList<Genre> = ObservableArrayList()
  val compositeDisposable: CompositeDisposable = CompositeDisposable()

  init {
    loadData()
  }

  fun loadData() {
    loadGenres()
  }

  private fun loadGenres() {
    val disposable = movieRepository.getGenres()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            genresObservable.clear()
            genresObservable.addAll(it.genres)
        }, { throwable -> handleError(throwable) })
    compositeDisposable.add(disposable)
  }

  fun handleError(t: Throwable) {

  }

  fun onCleared() {
    compositeDisposable.clear()
  }
}