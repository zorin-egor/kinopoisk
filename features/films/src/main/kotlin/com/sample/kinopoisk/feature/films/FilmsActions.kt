package com.sample.kinopoisk.feature.films


sealed interface FilmsActions {
    data object None : FilmsActions
    data class ShowError(val error: Throwable) : FilmsActions
}