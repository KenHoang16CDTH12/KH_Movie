package com.sun.moviesun.data.source.remote.connect

interface InternetConnectionListener {
  fun onInternetAvailable()
  fun onInternetUnavailable()
}
