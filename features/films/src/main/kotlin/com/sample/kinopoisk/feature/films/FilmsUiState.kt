package com.sample.kinopoisk.feature.films

import com.sample.kinopoisk.core.model.Film
import kotlinx.collections.immutable.ImmutableList

sealed interface FilmsUiState {
    data class Success(
        val films: ImmutableList<Film>,
        val genres: ImmutableList<GenreUi>
    ) : FilmsUiState
    data object Loading : FilmsUiState
    data object Empty : FilmsUiState
}

data class GenreUi(
    val isChecked: Boolean,
    val genre: String
)

val emptyGenre: GenreUi = GenreUi(
    isChecked = false,
    genre = ""
)