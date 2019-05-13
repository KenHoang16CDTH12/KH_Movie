package com.sun.moviesun.data.source.remote.connect

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

abstract class NetworkConnectionInterceptor : Interceptor {

  abstract fun isInternetAvailable(): Boolean
  abstract fun onInternetAvailable()
  abstract fun onInternetUnavailable()

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    when(isInternetAvailable()) {
      true -> onInternetAvailable()
      false -> onInternetUnavailable()
    }
    return chain.proceed(chain.request())
  }
}
