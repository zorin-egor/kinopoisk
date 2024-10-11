package com.sample.kinopoisk.di

import com.sample.kinopoisk.BuildConfig
import com.sample.kinopoisk.core.model.AppConfig
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class AppModule {

    @Singleton
    fun provideAppConfig() = AppConfig(
        appName = BuildConfig.APP_NAME,
        appVersion = BuildConfig.VERSION_NAME,
        appCode = BuildConfig.VERSION_CODE,
        baseUrl = "https://s3-eu-west-1.amazonaws.com"
    )

}