package com.sample.kinopoisk.core.network.ext

import com.sample.kinopoisk.core.network.exceptions.EmptyException
import com.sample.kinopoisk.core.network.exceptions.NetworkException
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.HttpURLConnection

fun ResponseBody.getError(): String? = runCatching { string() }
    .getOrNull()?.jsonToObject<String>()

fun <T> Response<T>.getResultOrThrow(): T {
    return when(val code = code()) {
        in HttpURLConnection.HTTP_OK until HttpURLConnection.HTTP_MULT_CHOICE -> body()
            ?: throw EmptyException
        else -> throw NetworkException(errorCode = code, errorDesc = errorBody()?.getError())
    }
}