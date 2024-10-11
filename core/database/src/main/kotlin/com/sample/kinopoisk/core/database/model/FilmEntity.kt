package com.sample.kinopoisk.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sample.kinopoisk.core.model.Film

@Entity(
    tableName = "Films",
    indices = [ Index(value = ["film_id"]) ]
)
data class FilmEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "film_id") val filmId: Long,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "genres") val genres: List<String>,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "localized_name") val localizedName: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "rating") val rating: Double?,
    @ColumnInfo(name = "year") val year: Int
)

fun FilmEntity.toModel() = Film(
    id = filmId,
    description = description,
    genres = genres,
    imageUrl = imageUrl,
    localizedName = localizedName,
    name = name,
    rating = rating,
    year = year
)

fun List<FilmEntity>.toModels() = map { it.toModel() }