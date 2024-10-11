package com.sample.kinopoisk.core.domain.di

import com.sample.kinopoisk.core.common.di.Dispatcher
import com.sample.kinopoisk.core.common.di.Dispatchers
import com.sample.kinopoisk.core.data.repositories.films.FilmsRepository
import com.sample.kinopoisk.core.domain.usecases.GetFilmsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class DomainModule {

    @Singleton
     fun provideGetFilmsUseCase(
        filmsRepository: FilmsRepository,
        @Dispatcher(Dispatchers.IO) dispatcher: CoroutineDispatcher
    ): GetFilmsUseCase = GetFilmsUseCase(filmsRepository, dispatcher)

}
