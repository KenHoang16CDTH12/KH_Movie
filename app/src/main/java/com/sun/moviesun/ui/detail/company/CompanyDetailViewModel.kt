package com.sun.moviesun.ui.detail.company

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import com.sun.moviesun.data.model.CompanyDetail
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.data.repository.MovieRepository
import com.sun.moviesun.ui.home.discover.MovieAdapter
import com.sun.moviesun.util.OnItemRecyclerViewClick
import com.sun.moviesun.util.extension.notNull
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CompanyDetailViewModel constructor(
    private val movieRepository: MovieRepository,
    private val navigator: CompanyDetailNavigator
) : BaseObservable(), OnItemRecyclerViewClick<Movie> {

  private var companyId: Int = 0
  val adapterMovieObservable: ObservableField<MovieAdapter> = ObservableField()
  val companyDetailObservable: ObservableField<CompanyDetail> = ObservableField()
  val compositeDisposable: CompositeDisposable = CompositeDisposable()

  fun setCompanyId(companyId: Int) {
    this.companyId = companyId
  }

  init {
    setUpAdapter()
  }

  private fun setUpAdapter() {
    adapterMovieObservable.set(MovieAdapter(this))
  }

  fun loadData() {
    loadCompanyDetail()
    loadMovies()
  }

  fun loadCompanyDetail() {
    val disposable = movieRepository.getCompany(companyId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          companyDetailObservable.set(it)
        }, { throwable -> handleError(throwable) })
    compositeDisposable.add(disposable)
  }

  fun loadMovies(page: Int = DEFAULT_PAGE) {
    val disposable = movieRepository.getMoviesByCompany(companyId, page)
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