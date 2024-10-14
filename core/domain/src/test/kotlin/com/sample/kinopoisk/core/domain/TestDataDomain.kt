package com.sample.kinopoisk.core.domain

import com.sample.kinopoisk.core.model.Film
import kotlin.random.Random

private val genres get() = arrayOf("genre1", "genre2", "genre3")

fun getFilm(
    id: Long,
    rating: Double? = Random.nextDouble(10.0),
): Film = Film(
    id = id,
    description = "description$id",
    genres = listOf(genres[id.toInt() % 3]),
    imageUrl = "imageUrl$id",
    localizedName = "localizedName$id",
    name = "name$id",
    rating = rating,
    year = 1950 + Random.nextInt(75),
)