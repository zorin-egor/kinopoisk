package com.sample.kinopoisk.core.network.di

import com.sample.kinopoisk.core.common.di.Dispatcher
import com.sample.kinopoisk.core.common.di.Dispatchers
import com.sample.kinopoisk.core.network.NetworkDataSource
import com.sample.kinopoisk.core.network.dev.DevAssetManager
import com.sample.kinopoisk.core.network.dev.DevNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
internal class NetworkApiModule {

    @Singleton
    fun provideNetworkDataSource(
        json: Json,
        @Dispatcher(Dispatchers.IO) dispatcher: CoroutineDispatcher,
        devAssetManager: DevAssetManager
    ): NetworkDataSource = DevNetworkDataSource(json, dispatcher, devAssetManager)

}
