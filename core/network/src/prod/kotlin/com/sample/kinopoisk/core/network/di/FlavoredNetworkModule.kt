package com.sample.kinopoisk.core.network.di

import com.sample.kinopoisk.core.model.AppConfig
import com.sample.kinopoisk.core.network.NetworkDataSource
import com.sample.kinopoisk.core.network.retrofit.RetrofitNetwork
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
internal class NetworkApiModule {

    @Singleton
    fun provideNetworkDataSource(
        appConfig: AppConfig,
        okHttpClient: OkHttpClient,
        json: Json,
    ): NetworkDataSource = RetrofitNetwork(appConfig, okHttpClient, json)

}
