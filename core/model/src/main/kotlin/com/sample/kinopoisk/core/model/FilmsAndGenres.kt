package com.sample.kinopoisk.core.model

data class FilmsAndGenres(
    val films: List<Film>,
    val genres: Set<String>
)
