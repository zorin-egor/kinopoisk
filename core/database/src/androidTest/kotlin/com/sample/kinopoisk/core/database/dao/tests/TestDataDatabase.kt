package com.sample.kinopoisk.core.database.dao.tests

import com.sample.kinopoisk.core.database.model.FilmEntity
import kotlin.random.Random

fun getFilmEntity(
    filmId: Long,
    rating: Double? = Random.nextDouble(10.0),
): FilmEntity = FilmEntity(
    id = Random.nextLong(),
    filmId = filmId,
    description = "description$filmId",
    genres = listOf("genres$filmId"),
    imageUrl = "imageUrl$filmId",
    localizedName = "localizedName$filmId",
    name = "name$filmId",
    rating = rating,
    year = 1950 + Random.nextInt(75),
)