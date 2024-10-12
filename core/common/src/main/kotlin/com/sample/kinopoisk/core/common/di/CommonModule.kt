package com.sample.kinopoisk.core.common.di

import android.content.Context
import com.sample.kinopoisk.core.common.di.Dispatchers.Default
import com.sample.kinopoisk.core.common.di.Dispatchers.IO
import com.sample.kinopoisk.core.common.network.ConnectivityManagerNetworkMonitor
import com.sample.kinopoisk.core.common.network.NetworkMonitor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class CommonModule {

    @Singleton
    fun provideNetworkMonitor(context: Context): NetworkMonitor = ConnectivityManagerNetworkMonitor(context)

    @Factory
    @Dispatcher(IO)
    fun provideIODispatcher(context: Context) = Dispatchers.IO

    @Factory
    @Dispatcher(Default)
    fun provideDefaultDispatcher(context: Context) = Dispatchers.Default

    @Singleton
    @DefaultScope
    fun providesDefaultCoroutineScope(
        @Dispatcher(Default) dispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    @Singleton
    @IoScope
    fun providesIoCoroutineScope(
        @Dispatcher(IO) dispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

}


