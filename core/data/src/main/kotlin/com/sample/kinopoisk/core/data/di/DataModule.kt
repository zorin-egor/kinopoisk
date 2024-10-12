package com.sample.kinopoisk.core.data.di

import com.sample.kinopoisk.core.common.di.IoScope
import com.sample.kinopoisk.core.data.repositories.films.FilmsRepository
import com.sample.kinopoisk.core.data.repositories.films.FilmsRepositoryImpl
import com.sample.kinopoisk.core.database.dao.FilmsDao
import com.sample.kinopoisk.core.network.NetworkDataSource
import kotlinx.coroutines.CoroutineScope
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class DataModule {

    @Singleton
     fun provideFilmsRepository(
        networkDataSource: NetworkDataSource,
        filmsDao: FilmsDao,
        @IoScope ioScope: CoroutineScope
    ): FilmsRepository = FilmsRepositoryImpl(filmsDao, networkDataSource, ioScope)

}
