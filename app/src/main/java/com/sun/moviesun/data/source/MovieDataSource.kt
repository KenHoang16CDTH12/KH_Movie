package com.sun.moviesun.data.source

import com.sun.moviesun.data.model.CompanyDetail
import com.sun.moviesun.data.model.MovieDetail
import com.sun.moviesun.data.model.PersonDetail
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.data.source.remote.response.*
import io.reactivex.*

interface MovieDataSource {
  interface Local {
    fun insertMovie(movie: Movie)
    fun updateMovie(movie: Movie)
    fun getMovie(id: Int): Maybe<Movie>
    fun getMovies(): Flowable<List<Movie>>
    fun deleteMovie(id: Int)
  }

  interface Remote {
    fun getMoviesTrendingByDay(): Observable<MovieResponse>
    fun getMoviesCategory(category: String?, page: Int): Observable<MovieResponse>
    fun getKeywords(id: Int): Observable<KeywordResponse>
    fun getVideos(id: Int): Observable<VideoResponse>
    fun getReviews(id: Int, page: Int): Observable<ReviewResponse>
    fun getMovie(id: Int): Observable<MovieDetail>
    fun getCredits(id: Int): Observable<CreditResponse>
    fun getGenres(): Observable<GenresResponse>
    fun getMoviesByGenre(genreId: Int, page: Int): Observable<MovieResponse>
    fun getMoviesByCast(castId: Int, page: Int): Observable<MovieResponse>
    fun getMoviesByCompany(companyId: Int, page: Int): Observable<MovieResponse>
    fun getCompany(companyId: Int): Observable<CompanyDetail>
    fun getPopularPeople(page: Int): Observable<PersonResponse>
    fun getPersonDetail(id: Int): Observable<PersonDetail>
    fun searchMovie(keyword: String, page: Int): Observable<MovieResponse>
  }
}
