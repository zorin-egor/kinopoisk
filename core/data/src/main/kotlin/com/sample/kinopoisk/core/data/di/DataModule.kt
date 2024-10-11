package com.sample.kinopoisk.core.data.di

import com.sample.kinopoisk.core.data.repositories.films.FilmsRepository
import com.sample.kinopoisk.core.data.repositories.films.FilmsRepositoryImpl
import com.sample.kinopoisk.core.database.dao.FilmsDao
import com.sample.kinopoisk.core.network.NetworkDataSource
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class DataModule {

    @Singleton
     fun provideFilmsRepository(
        networkDataSource: NetworkDataSource,
        filmsDao: FilmsDao
    ): FilmsRepository = FilmsRepositoryImpl(filmsDao, networkDataSource)

}
