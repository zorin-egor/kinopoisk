package com.sample.kinopoisk.core.network.di

import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.sample.kinopoisk.core.network.BuildConfig
import com.sample.kinopoisk.core.network.dev.DevAssetManager
import com.sample.kinopoisk.core.network.interceptors.NetworkInterceptor
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module(includes = [ NetworkApiModule::class ])
class NetworkModule {

    @Singleton
    fun providesDevAssetManager(context: Context): DevAssetManager = DevAssetManager(context.assets::open)

    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @Singleton
    fun okHttpCallFactory(context: Context): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(NetworkInterceptor(context))
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                },
        ).build()

    @Singleton
    fun imageLoader(
        okHttpClient: OkHttpClient,
        context: Context,
    ): ImageLoader = ImageLoader.Builder(context)
        .callFactory { okHttpClient }
        .components { add(SvgDecoder.Factory()) }
        .respectCacheHeaders(false)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .apply {
            if (BuildConfig.DEBUG) {
                logger(DebugLogger())
            }
        }
        .build()

}