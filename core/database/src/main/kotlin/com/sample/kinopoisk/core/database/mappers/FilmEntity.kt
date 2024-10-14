package com.sample.kinopoisk.core.database.mappers

import com.sample.kinopoisk.core.database.model.FilmEntity
import com.sample.kinopoisk.core.model.Film

fun Film.toEntity(): FilmEntity = FilmEntity(
    filmId = id,
    description = description,
    genres = genres,
    imageUrl = imageUrl,
    localizedName = localizedName,
    name = name,
    rating = rating,
    year = year,
)