package com.sample.kinopoisk.core.database.dao.tests

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.sample.kinopoisk.core.database.AppDatabase
import com.sample.kinopoisk.core.database.dao.FilmsDao
import com.sample.kinopoisk.core.database.model.FilmEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class AppDatabaseDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var filmsDao: FilmsDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java,
        ).build()

        filmsDao = db.filmsDao()
    }

    @After
    fun closeDb() = db.close()

    @Test
    fun getEmptyFilmsTest() = runTest {
        val films = filmsDao.getFilms()
            .take(1)
            .first()
        assertEquals(emptyList(), films)
    }

    @Test
    fun getAllFilmsTest() = runTest {
        val filmsEntities = mutableListOf<FilmEntity>()
        repeat(5) {
            filmsEntities.add(getFilmEntity(it.toLong()))
        }

        filmsDao.insert(filmsEntities)

        val films = filmsDao.getFilms()
            .take(1)
            .first()

        assertEquals(filmsEntities, films)
    }

    @Test
    fun getEmptyFilmByIdTest() = runTest {
        val film = filmsDao.getFilmById(-1)
            .take(1)
            .firstOrNull()
        assertEquals(null, film)
    }

}

