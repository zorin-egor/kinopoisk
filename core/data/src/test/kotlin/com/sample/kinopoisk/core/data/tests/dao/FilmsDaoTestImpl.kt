package com.sample.kinopoisk.core.data.tests.dao

import com.sample.kinopoisk.core.database.dao.FilmsDao
import com.sample.kinopoisk.core.database.model.FilmEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FilmsDaoTestImpl : FilmsDao {

    private val dbStateFlow = MutableStateFlow(emptyList<FilmEntity>())

    override fun getFilms(): Flow<List<FilmEntity>> = dbStateFlow.asStateFlow()

    override fun getFilmById(filmId: Long): Flow<FilmEntity?> =
        dbStateFlow.map { flow ->
            flow.filter { it.filmId == filmId }.let {
                if (it.size > 1) throw IllegalStateException("More then 1 matches")
                it.firstOrNull()
            }
        }

    override suspend fun insert(item: FilmEntity) =
        dbStateFlow.update { flow -> (flow + item).distinctBy(FilmEntity::filmId) }

    override suspend fun insert(items: List<FilmEntity>) =
        dbStateFlow.update { flow -> (flow + items).distinctBy(FilmEntity::filmId) }

    override suspend fun delete(item: FilmEntity) =
        dbStateFlow.update { flow -> flow.toMutableList().apply { remove(item) } }

    override suspend fun delete() =
        dbStateFlow.update { flow -> flow.toMutableList().apply { clear() } }

}