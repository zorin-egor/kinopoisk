package com.sample.kinopoisk.core.database.di

import android.content.Context
import androidx.room.Room
import com.sample.kinopoisk.core.database.AppDatabase
import com.sample.kinopoisk.core.database.dao.FilmsDao
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton


@Module
class DatabaseModule {

    @Singleton
    fun providesAppDatabase(context: Context, ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app-database",
    ).build()

    @Singleton
    fun providesFilmsDao(database: AppDatabase): FilmsDao = database.filmsDao()
}
