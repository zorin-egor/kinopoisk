package com.sample.kinopoisk.feature.film_details

import com.sample.kinopoisk.core.model.Film

sealed interface FilmDetailsUiState {
    data class Success(val users: Film) : FilmDetailsUiState
    data object Loading : FilmDetailsUiState
    data object Empty : FilmDetailsUiState
}