package com.sun.moviesun.ui.home.discover

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import com.sun.moviesun.data.annotation.CategoryKeyDef
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.data.repository.MovieRepository
import com.sun.moviesun.util.OnItemRecyclerViewClick
import com.sun.moviesun.util.extension.notNull
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DiscoverViewModel constructor(
    private val movieRepository: MovieRepository,
    private val navigator: DiscoverNavigator
) : BaseObservable(), OnItemRecyclerViewClick<Movie> {

  val adapterTopTrendingObservable: ObservableField<SliderAdapter> = ObservableField()
  val adapterPopularObservable: ObservableField<MovieAdapter> = ObservableField()
  val adapterNowPlayingObservable: ObservableField<MovieAdapter> = ObservableField()
  val adapterUpComingObservable: ObservableField<MovieAdapter> = ObservableField()
  val adapterTopRateObservable: ObservableField<MovieAdapter> = ObservableField()
  private val compositeDisposable: CompositeDisposable = CompositeDisposable()

  init {
    setUpAdapter()
    loadData()
  }

  private fun setUpAdapter() {
    adapterTopTrendingObservable.set(SliderAdapter(this))
    adapterPopularObservable.set(MovieAdapter(this))
    adapterNowPlayingObservable.set(MovieAdapter(this))
    adapterUpComingObservable.set(MovieAdapter(this))
    adapterTopRateObservable.set(MovieAdapter(this))
  }

  fun loadData() {
    loadTrendingMovies()
    loadMovies(CategoryKeyDef.TOP_RATED)
    loadMovies(CategoryKeyDef.NOW_PLAYING)
    loadMovies(CategoryKeyDef.POPULAR)
    loadMovies(CategoryKeyDef.UPCOMING)
  }

  fun loadTrendingMovies() {
    val disposable = movieRepository.getMoviesTrendingByDay()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            {
              adapterTopTrendingObservable.get()!!.updateData(it.results.subList(FIRST_INDEX, LAST_INDEX))
            }
            ,
            { throwable -> handleError(throwable) })
    compositeDisposable.add(disposable)
  }

  fun loadMovies(category: String = CategoryKeyDef.DEFAULT, page: Int = DEFAULT_PAGE) {
    val disposable = movieRepository.getMoviesCategory(category, page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            {
              when (category) {
                CategoryKeyDef.TOP_RATED -> {
                  if (page == DEFAULT_PAGE)
                    adapterTopRateObservable.get()!!.replaceItems(it.results)
                  else
                    adapterTopRateObservable.get()!!.addItems(it.results)
                }
                CategoryKeyDef.NOW_PLAYING -> {
                  if (page == DEFAULT_PAGE)
                    adapterNowPlayingObservable.get()!!.replaceItems(it.results)
                  else
                    adapterNowPlayingObservable.get()!!.addItems(it.results)
                }
                CategoryKeyDef.POPULAR -> {
                  if (page == DEFAULT_PAGE)
                    adapterPopularObservable.get()!!.replaceItems(it.results)
                  else
                    adapterPopularObservable.get()!!.addItems(it.results)
                }
                CategoryKeyDef.UPCOMING -> {
                  if (page == DEFAULT_PAGE)
                    adapterUpComingObservable.get()!!.replaceItems(it.results)
                  else
                    adapterUpComingObservable.get()!!.addItems(it.results)
                }
              }
            },
            { throwable -> handleError(throwable) })
    compositeDisposable.add(disposable)
  }

  override fun onItemClickListener(data: Movie) {
    navigator.notNull { it.onClickItemMovie(data) }
  }

  fun handleError(t: Throwable) {

  }

  fun onCleared() {
    compositeDisposable.clear()
  }

  companion object {
    private const val DEFAULT_PAGE = 1
    private const val FIRST_INDEX = 0
    private const val LAST_INDEX = 5
  }
}
