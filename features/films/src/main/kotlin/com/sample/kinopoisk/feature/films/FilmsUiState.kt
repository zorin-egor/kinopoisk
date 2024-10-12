package com.sample.kinopoisk.feature.films

import com.sample.kinopoisk.core.model.Film

sealed interface FilmsUiState {
    data class Success(
        val films: List<Film>,
        val genres: List<GenreUi>
    ) : FilmsUiState
    data object Loading : FilmsUiState
    data object Empty : FilmsUiState
}

data class GenreUi(
    val isChecked: Boolean,
    val genre: String
)