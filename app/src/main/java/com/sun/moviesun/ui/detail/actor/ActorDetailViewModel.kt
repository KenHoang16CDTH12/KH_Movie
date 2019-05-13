package com.sun.moviesun.ui.detail.actor

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import com.sun.moviesun.data.model.PersonDetail
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.data.repository.MovieRepository
import com.sun.moviesun.ui.home.discover.MovieAdapter
import com.sun.moviesun.util.OnItemRecyclerViewClick
import com.sun.moviesun.util.extension.notNull
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ActorDetailViewModel constructor(
    private val movieRepository: MovieRepository,
    private val navigator: ActorDetailNavigator
) : BaseObservable(), OnItemRecyclerViewClick<Movie> {

  private var peopleId: Int = 0
  val adapterMovieObservable: ObservableField<MovieAdapter> = ObservableField()
  val personDetailObservable: ObservableField<PersonDetail> = ObservableField()
  val compositeDisposable: CompositeDisposable = CompositeDisposable()

  fun setPeopleId(peopleId: Int) {
    this.peopleId = peopleId
  }

  init {
    setUpAdapter()
  }

  private fun setUpAdapter() {
    adapterMovieObservable.set(MovieAdapter(this))
  }

  fun loadData() {
    loadPersonDetail()
    loadMovies()
  }

  fun loadPersonDetail() {
    val disposable = movieRepository.getPersonDetail(peopleId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          personDetailObservable.set(it)
        }, { throwable -> handleError(throwable) })
    compositeDisposable.add(disposable)
  }

  fun loadMovies(page: Int = DEFAULT_PAGE) {
    val disposable = movieRepository.getMoviesByCast(peopleId, page)
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