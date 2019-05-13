package com.sun.moviesun.ui.detail.movie

import androidx.databinding.*
import com.sun.moviesun.BR
import com.sun.moviesun.R
import com.sun.moviesun.data.model.*
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.data.repository.MovieRepository
import com.sun.moviesun.ui.detail.movie.adapter.*
import com.sun.moviesun.util.OnItemRecyclerViewClick
import com.sun.moviesun.util.extension.notNull
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailViewModel constructor(
    private val movieRepository: MovieRepository,
    private val navigator: MovieDetailNavigator
) : BaseObservable() {

  private lateinit var movie: Movie

  fun setMovie(movie: Movie) {
    this.movie = movie
    notifyPropertyChanged(BR.movie)
  }

  @Bindable
  fun getMovie() = movie

  val keywordsObservable: ObservableList<Keyword> = ObservableArrayList()
  val adapterVideoObservable: ObservableField<VideoAdapter> = ObservableField()
  val adapterReviewObservable: ObservableField<ReviewAdapter> = ObservableField()
  val adapterCastObservable: ObservableField<CastAdapter> = ObservableField()
  val adapterCrewObservable: ObservableField<CrewAdapter> = ObservableField()
  val adapterCompanyObservable: ObservableField<CompanyAdapter> = ObservableField()
  val movieDetailObservable: ObservableField<MovieDetail> = ObservableField()
  val isFavoriteMovieObservable = ObservableBoolean(false)
  val compositeDisposable: CompositeDisposable = CompositeDisposable()

  init {
    setUpAdapter()
  }

  private fun setUpAdapter() {
    adapterVideoObservable.set(VideoAdapter(object : OnItemRecyclerViewClick<Video> {
      override fun onItemClickListener(data: Video) {
        navigator.notNull { it.onClickItemVideo(data) }
      }
    }))
    adapterReviewObservable.set(ReviewAdapter(object : OnItemRecyclerViewClick<Review> {
      override fun onItemClickListener(data: Review) {
        navigator.notNull { it.onClickItemReview(data) }
      }
    }))
    adapterCastObservable.set(CastAdapter(object : OnItemRecyclerViewClick<Cast> {
      override fun onItemClickListener(data: Cast) {
        navigator.notNull { it.onClickItemCast(data) }
      }
    }))
    adapterCrewObservable.set(CrewAdapter(object : OnItemRecyclerViewClick<Crew> {
      override fun onItemClickListener(data: Crew) {
        navigator.notNull { it.onClickItemCrew(data) }
      }
    }))
    adapterCompanyObservable.set(CompanyAdapter(object : OnItemRecyclerViewClick<Company> {
      override fun onItemClickListener(data: Company) {
        navigator.notNull { it.onClickItemCompany(data) }
      }
    }))
  }

  fun loadData() {
    checkMovieFavorite()
    loadVideo()
    loadKeyword()
    loadActors()
    loadMovieDetails()
    loadReviews()
  }

  fun checkMovieFavorite() {
    val disposable = movieRepository.getMovieFavorite(movie.id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          isFavoriteMovieObservable.set(true)
        }, { handleError(it) })
    compositeDisposable.add(disposable)
  }

  fun loadKeyword() {
    val disposable = movieRepository.getKeywords(movie.id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          keywordsObservable.clear()
          keywordsObservable.addAll(it.keywords)
        }, { throwable -> handleError(throwable) })
    compositeDisposable.add(disposable)
  }

  fun loadVideo() {
    val disposable = movieRepository.getVideos(movie.id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          adapterVideoObservable.get()!!.replaceItems(it.results)
        }, { throwable -> handleError(throwable) })
    compositeDisposable.add(disposable)
  }

  fun loadActors() {
    val disposable = movieRepository.getCredits(movie.id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          adapterCastObservable.get()!!.replaceItems(it.cast)
          adapterCrewObservable.get()!!.replaceItems(it.crew)
        }, { throwable -> handleError(throwable) })
    compositeDisposable.add(disposable)
  }

  fun loadMovieDetails() {
    val disposable = movieRepository.getMovie(movie.id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          movieDetailObservable.set(it)
          adapterCompanyObservable.get()!!.replaceItems(it.productionCompanies!!)
        }, { throwable -> handleError(throwable) })
    compositeDisposable.add(disposable)
  }

  fun loadReviews(page: Int = DEFAULT_PAGE) {
    val disposable = movieRepository.getReviews(movie.id, page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          if (page == DEFAULT_PAGE)
            adapterReviewObservable.get()!!.replaceItems(it.results)
          else
            adapterReviewObservable.get()!!.addItems(it.results)
        }, { throwable -> handleError(throwable) })
    compositeDisposable.add(disposable)
  }

  fun onFavoriteClick() {
    if (isFavoriteMovieObservable.get()) {
      deleteFavorite()
      navigator.notifySnackbar(R.string.notify_dislike)
    } else {
      insertFavorite()
      navigator.notifySnackbar(R.string.notify_like)
    }
  }

  private fun insertFavorite() {
    movieRepository.insertMovieFavorite(movie)
    isFavoriteMovieObservable.set(true)
  }

  private fun deleteFavorite() {
    movieRepository.deleteMovieFavorite(movie.id)
    isFavoriteMovieObservable.set(false)
  }

  fun handleError(t: Throwable) {

  }

  fun onCleared() {
    compositeDisposable.clear()
  }

  companion object {
    private const val DEFAULT_PAGE = 1
  }
}
