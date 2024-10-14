package com.sample.kinopoisk.core.network.interceptors

import android.content.Context
import com.sample.kinopoisk.core.common.extensions.isOnline
import com.sample.kinopoisk.core.network.exceptions.NetworkException
import okhttp3.Interceptor
import okhttp3.Response

internal class NetworkInterceptor(private val context : Context) : Interceptor {

    @Throws(NetworkException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isOnline()) {
            throw NetworkException()
        }
        return chain.proceed(chain.request())
    }

}