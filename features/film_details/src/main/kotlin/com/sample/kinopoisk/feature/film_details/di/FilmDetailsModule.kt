package com.sample.kinopoisk.feature.film_details.di

import com.sample.kinopoisk.core.domain.usecases.GetFilmByIdUseCase
import com.sample.kinopoisk.feature.film_details.FilmDetailsViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module
class FilmDetailsModule {

    @KoinViewModel
    fun provideFilmDetailsViewModel(getFilmByIdUseCase: GetFilmByIdUseCase, id: Long) =
        FilmDetailsViewModel(getFilmByIdUseCase, id)

}