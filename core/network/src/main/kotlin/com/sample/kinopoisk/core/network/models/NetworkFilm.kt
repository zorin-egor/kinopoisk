package com.sample.kinopoisk.core.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkFilm(
    @SerialName("id") val id: Long,
    @SerialName("description") val description: String?,
    @SerialName("genres") val genres: List<String>,
    @SerialName("image_url") val imageUrl: String?,
    @SerialName("localized_name") val localizedName: String,
    @SerialName("name") val name: String,
    @SerialName("rating") val rating: Double?,
    @SerialName("year") val year: Int
)