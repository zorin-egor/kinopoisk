package com.sample.kinopoisk.core.data.mapper

import com.sample.kinopoisk.core.database.model.FilmEntity
import com.sample.kinopoisk.core.model.Film
import com.sample.kinopoisk.core.network.models.NetworkFilm

internal fun NetworkFilm.toModel(): Film = Film(
    id = id,
    description = description,
    genres = genres,
    imageUrl = imageUrl,
    localizedName = localizedName,
    name = name,
    rating = rating,
    year = year
)

internal fun List<NetworkFilm>.toModels(): List<Film> = map { it.toModel() }

internal fun NetworkFilm.toEntity() = FilmEntity(
    filmId = id,
    description = description,
    genres = genres,
    imageUrl = imageUrl,
    localizedName = localizedName,
    name = name,
    rating = rating,
    year = year
)

internal fun List<NetworkFilm>.toEntities(): List<FilmEntity> = map { it.toEntity() }