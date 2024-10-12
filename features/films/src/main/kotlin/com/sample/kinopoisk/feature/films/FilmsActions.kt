package com.sample.kinopoisk.feature.films


sealed interface FilmsActions {
    data class ShowError(val error: Throwable) : FilmsActions
}