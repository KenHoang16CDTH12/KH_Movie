package com.sun.moviesun.data.source.remote.connect

import android.content.Context
import com.sun.moviesun.data.source.remote.api.Api
import com.sun.moviesun.data.source.remote.api.MovieService
import com.sun.moviesun.util.extension.isNetworkStatusAvailable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


class RetrofitClient constructor(private val context: Context) {

  private var movieService: MovieService? = null
  private var internetConnectionListener: InternetConnectionListener? = null

  fun setInternetConnectionListener(listener: InternetConnectionListener) {
    internetConnectionListener = listener
  }

  fun getMovieService(): MovieService? {
    if (movieService == null) {
      movieService = provideRetrofit().create(MovieService::class.java)
    }
    return movieService
  }

  private fun provideRetrofit(): Retrofit {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BASIC
    val httpClientBuilder = OkHttpClient.Builder()
        .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(RequestInterceptor())
        .addInterceptor(logging)
        .addInterceptor(object : NetworkConnectionInterceptor() {
          override fun isInternetAvailable(): Boolean = checkInternetAvailable()

          override fun onInternetAvailable() {
            internetConnectionListener?.onInternetAvailable()
          }

          override fun onInternetUnavailable() {
            internetConnectionListener?.onInternetUnavailable()
          }
        })
    return Retrofit.Builder()
        .baseUrl(Api.getUrl())
        .client(httpClientBuilder.build())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
  }

  private fun checkInternetAvailable(): Boolean = context.isNetworkStatusAvailable()

  companion object {
    private const val WRITE_TIMEOUT = 5000L
    private const val READ_TIMEOUT = 5000L
    private const val CONNECT_TIMEOUT = 8000L
  }
}
