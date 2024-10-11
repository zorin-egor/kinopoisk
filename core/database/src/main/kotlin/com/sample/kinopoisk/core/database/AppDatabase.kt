package com.sample.kinopoisk.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sample.kinopoisk.core.database.converters.ListStringConverter
import com.sample.kinopoisk.core.database.dao.FilmsDao
import com.sample.kinopoisk.core.database.model.FilmEntity

@Database(
    entities = [
        FilmEntity::class,
    ],
    version = 1,
    autoMigrations = [],
    exportSchema = true
)
@TypeConverters(
    ListStringConverter::class,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filmsDao(): FilmsDao

}
