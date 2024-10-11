package com.sample.kinopoisk.core.data.mapper

import com.sample.kinopoisk.core.model.Film
import com.sample.kinopoisk.core.network.models.NetworkFilm

fun NetworkFilm.toModel(): Film = Film(
    id = id,
    description = description,
    genres = genres,
    imageUrl = imageUrl,
    localizedName = localizedName,
    name = name,
    rating = rating,
    year = year
)

fun List<NetworkFilm>.toModels(): List<Film> = map { it.toModel() }