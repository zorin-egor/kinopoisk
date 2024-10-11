package com.sample.kinopoisk.core.network.retrofit


import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sample.kinopoisk.core.model.AppConfig
import com.sample.kinopoisk.core.network.NetworkDataSource
import com.sample.kinopoisk.core.network.models.NetworkFilms
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit


internal class RetrofitNetwork(
    appConfig: AppConfig,
    okHttpClient: OkHttpClient,
    json: Json,
) : NetworkDataSource {

    private var api = Retrofit.Builder()
        .baseUrl(appConfig.baseUrl)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()),)
        .build()
        .create(RetrofitApi::class.java)

    override suspend fun getFilms(): Response<NetworkFilms> = api.getFilms()

}

