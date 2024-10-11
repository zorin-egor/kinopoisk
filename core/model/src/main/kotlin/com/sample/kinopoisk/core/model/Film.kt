package com.sample.kinopoisk.core.model

data class Film(
    val id: Long,
    val description: String?,
    val genres: List<String>,
    val imageUrl: String?,
    val localizedName: String,
    val name: String,
    val rating: Double?,
    val year: Int
)