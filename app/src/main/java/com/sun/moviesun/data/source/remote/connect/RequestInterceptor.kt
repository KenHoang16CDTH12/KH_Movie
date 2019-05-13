package com.sun.moviesun.data.source.remote.connect

import com.sun.moviesun.BuildConfig
import com.sun.moviesun.data.source.remote.api.Api.getApiKey
import okhttp3.Interceptor
import okhttp3.Response

internal class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().let {
            it.newBuilder().url(
                it.url().newBuilder()
                    .addQueryParameter(getApiKey(), BuildConfig.MOVIE_DB_API_KEY)
                    .build()
            ).build()
        })
    }
}
