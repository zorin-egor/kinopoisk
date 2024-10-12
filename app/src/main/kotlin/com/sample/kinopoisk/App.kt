package com.sample.kinopoisk

import android.app.Application
import com.sample.kinopoisk.core.common.di.CommonModule
import com.sample.kinopoisk.core.common.di.NavigationModule
import com.sample.kinopoisk.core.data.di.DataModule
import com.sample.kinopoisk.core.database.di.DatabaseModule
import com.sample.kinopoisk.core.domain.di.DomainModule
import com.sample.kinopoisk.core.network.di.NetworkModule
import com.sample.kinopoisk.di.AppModule
import com.sample.kinopoisk.feature.film_details.di.FilmDetailsModule
import com.sample.kinopoisk.feature.films.di.FilmsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
import timber.log.Timber


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                AppModule().module,
                CommonModule().module,
                NetworkModule().module,
                DatabaseModule().module,
                DataModule().module,
                DomainModule().module,
                NavigationModule().module,
                FilmsModule().module,
                FilmDetailsModule().module
            )
        }
    }
}
