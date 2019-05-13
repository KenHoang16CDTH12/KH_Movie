package com.sun.moviesun.data.source.local.database.dao

import androidx.room.*
import com.sun.moviesun.data.model.entity.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface MovieDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertMovie(movie: Movie)

  @Update
  fun updateMovie(movie: Movie)

  @Query("SELECT * FROM movies WHERE id = :id")
  fun getMovie(id: Int): Maybe<Movie>

  @Query("SELECT * FROM movies")
  fun getMovies(): Flowable<List<Movie>>

  @Query("DELETE FROM movies WHERE id = :id")
  fun deleteMovie(id: Int)
}
