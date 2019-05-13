package com.sun.moviesun.data.repository

import com.sun.moviesun.data.annotation.CategoryKey
import com.sun.moviesun.data.model.CompanyDetail
import com.sun.moviesun.data.model.MovieDetail
import com.sun.moviesun.data.model.PersonDetail
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.data.source.local.MovieLocalDataSource
import com.sun.moviesun.data.source.remote.MovieRemoteDataSource
import com.sun.moviesun.data.source.remote.response.*
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable

interface MovieRepository {
  fun getMoviesTrendingByDay(): Observable<MovieResponse>
  fun getMoviesCategory(@CategoryKey category: String, page: Int): Observable<MovieResponse>
  fun getKeywords(id: Int): Observable<KeywordResponse>
  fun getVideos(id: Int): Observable<VideoResponse>
  fun getReviews(id: Int, page: Int): Observable<ReviewResponse>
  fun getMovie(id: Int): Observable<MovieDetail>
  fun getCredits(id: Int): Observable<CreditResponse>
  fun getGenres(): Observable<GenresResponse>
  fun getMoviesByCast(castId: Int, page: Int): Observable<MovieResponse>
  fun getMoviesByCompany(companyId: Int, page: Int): Observable<MovieResponse>
  fun getCompany(companyId: Int): Observable<CompanyDetail>
  fun getPopularPeople(page: Int): Observable<PersonResponse>
  fun getPersonDetail(id: Int): Observable<PersonDetail>
  fun getMoviesByGenre(genreId: Int, page: Int): Observable<MovieResponse>
  fun searchMovie(keyword: String, page: Int): Observable<MovieResponse>
  fun insertMovieFavorite(movie: Movie)
  fun updateMovieFavorite(movie: Movie)
  fun getMovieFavorite(id: Int): Maybe<Movie>
  fun getMoviesFavorite(): Flowable<List<Movie>>
  fun deleteMovieFavorite(id: Int)
}

class MovieRepositoryImpl constructor(
    private val local: MovieLocalDataSource,
    private val remote: MovieRemoteDataSource
) : MovieRepository {

  override fun getMoviesTrendingByDay(): Observable<MovieResponse> =
      remote.getMoviesTrendingByDay()

  override fun getMoviesCategory(@CategoryKey category: String, page: Int): Observable<MovieResponse> =
      remote.getMoviesCategory(category, page)

  override fun getKeywords(id: Int): Observable<KeywordResponse> =
      remote.getKeywords(id)

  override fun getVideos(id: Int): Observable<VideoResponse> =
      remote.getVideos(id)

  override fun getReviews(id: Int, page: Int): Observable<ReviewResponse> =
      remote.getReviews(id, page)

  override fun getMovie(id: Int): Observable<MovieDetail> =
      remote.getMovie(id)

  override fun getCredits(id: Int): Observable<CreditResponse> =
      remote.getCredits(id)

  override fun getGenres(): Observable<GenresResponse> =
      remote.getGenres()

  override fun getMoviesByCast(castId: Int, page: Int): Observable<MovieResponse> =
      remote.getMoviesByCast(castId, page)

  override fun getMoviesByCompany(companyId: Int, page: Int): Observable<MovieResponse> =
      remote.getMoviesByCompany(companyId, page)

  override fun getCompany(companyId: Int): Observable<CompanyDetail> =
      remote.getCompany(companyId)

  override fun getPopularPeople(page: Int): Observable<PersonResponse> =
      remote.getPopularPeople(page)

  override fun getPersonDetail(id: Int): Observable<PersonDetail> =
      remote.getPersonDetail(id)

  override fun getMoviesByGenre(genreId: Int, page: Int): Observable<MovieResponse> =
      remote.getMoviesByGenre(genreId, page)

  override fun searchMovie(keyword: String, page: Int): Observable<MovieResponse> =
      remote.searchMovie(keyword, page)

  override fun insertMovieFavorite(movie: Movie) { local.insertMovie(movie) }

  override fun updateMovieFavorite(movie: Movie) { local.updateMovie(movie) }

  override fun getMovieFavorite(id: Int): Maybe<Movie> = local.getMovie(id)

  override fun getMoviesFavorite(): Flowable<List<Movie>> = local.getMovies()

  override fun deleteMovieFavorite(id: Int) { local.deleteMovie(id) }

  companion object {
    private var sInstance: MovieRepositoryImpl? = null

    @JvmStatic
    fun getInstance(local: MovieLocalDataSource, remote: MovieRemoteDataSource): MovieRepository {
      if (sInstance == null) {
        synchronized(MovieRepositoryImpl::javaClass) {
          sInstance = MovieRepositoryImpl(local, remote)
        }
      }
      return sInstance!!
    }

    fun clearInstance() {
      sInstance = null
    }
  }
}
