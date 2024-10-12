package com.sample.kinopoisk.feature.film_details


sealed interface FilmDetailsActions {
    data class ShowError(val error: Throwable) : FilmDetailsActions
}