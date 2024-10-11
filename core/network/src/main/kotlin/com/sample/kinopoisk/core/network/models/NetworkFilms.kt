package com.sample.kinopoisk.core.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkFilms(
    @SerialName("films") val films: List<NetworkFilm>
)