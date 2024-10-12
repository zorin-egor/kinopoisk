package com.sample.kinopoisk.feature.films.di

import com.sample.kinopoisk.core.domain.usecases.GetFilmsUseCase
import com.sample.kinopoisk.feature.films.FilmsViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module
class FilmsModule {

    @KoinViewModel
    fun provideFilmsViewModel(getFilmsUseCase: GetFilmsUseCase) = FilmsViewModel(getFilmsUseCase)

}