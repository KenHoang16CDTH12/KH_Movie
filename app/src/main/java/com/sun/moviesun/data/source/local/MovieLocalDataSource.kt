package com.sun.moviesun.data.source.local

import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.data.source.MovieDataSource
import com.sun.moviesun.data.source.local.database.AppDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

class MovieLocalDataSource(
    database: AppDatabase
) : MovieDataSource.Local {

  private val movieDao = database.movieDao()

  override fun insertMovie(movie: Movie) { movieDao.insertMovie(movie) }

  override fun updateMovie(movie: Movie) { movieDao.updateMovie(movie) }

  override fun getMovie(id: Int): Maybe<Movie> = movieDao.getMovie(id)

  override fun getMovies(): Flowable<List<Movie>>  = movieDao.getMovies()

  override fun deleteMovie(id: Int) { movieDao.deleteMovie(id) }

  companion object {

    private var sInstance: MovieLocalDataSource? = null

    @JvmStatic
    fun getInstance(database: AppDatabase): MovieLocalDataSource {
      if (sInstance == null) {
        synchronized(MovieLocalDataSource::javaClass) {
          sInstance = MovieLocalDataSource(database)
        }
      }
      return sInstance!!
    }

    fun clearInstance() { sInstance = null }
  }
}
