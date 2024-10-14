package com.sample.kinopoisk.core.data.repositories.films

import com.sample.kinopoisk.core.common.result.Result
import com.sample.kinopoisk.core.common.result.combineLeftFirst
import com.sample.kinopoisk.core.common.result.startLoading
import com.sample.kinopoisk.core.data.mapper.toEntities
import com.sample.kinopoisk.core.data.mapper.toModel
import com.sample.kinopoisk.core.data.mapper.toModels
import com.sample.kinopoisk.core.database.dao.FilmsDao
import com.sample.kinopoisk.core.database.mappers.toEntity
import com.sample.kinopoisk.core.database.model.FilmEntity
import com.sample.kinopoisk.core.database.model.toModel
import com.sample.kinopoisk.core.database.model.toModels
import com.sample.kinopoisk.core.model.Film
import com.sample.kinopoisk.core.network.NetworkDataSource
import com.sample.kinopoisk.core.network.exceptions.EmptyException
import com.sample.kinopoisk.core.network.ext.getResultOrThrow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import timber.log.Timber


internal class FilmsRepositoryImpl(
    private val filmsDao: FilmsDao,
    private val networkDatasource: NetworkDataSource,
    private val ioScope: CoroutineScope
) : FilmsRepository {

    override fun getFilms(): Flow<Result<List<Film>>> {
        val dbFlow = filmsDao.getFilms()
            .take(1)
            .filterNot { it.isEmpty() }
            .map<List<FilmEntity>, Result<List<Film>>> { Result.Success(it.toModels()) }
            .onStart { emit(Result.Loading) }
            .catch {
                Timber.e(it)
                emit(Result.Error(it))
            }

        val nwFlow = flow {
            emit(Result.Loading)

            val response = networkDatasource.getFilms()
                .getResultOrThrow().films

            if (response.isNotEmpty()) {
                ioScope.launch {
                    runCatching { filmsDao.clearInsert(response.toEntities()) }
                        .onFailure(Timber::e)
                }
            }

            emit(Result.Success(response.toModels()))
        }.catch {
            Timber.e(it)
            emit(Result.Error(it))
        }

        return dbFlow.combineLeftFirst(nwFlow)
            .startLoading()
    }

    override fun getFilm(id: Long): Flow<Result<Film>> = flow<Result<Film>> {
        emit(Result.Loading)

        val dbFilm = filmsDao.getFilmById(id)
            .map { it?.toModel() }
            .catch { Timber.e(it) }
            .firstOrNull()

        if (dbFilm != null) {
            emit(Result.Success(dbFilm))
            return@flow
        }

        val nwFilms = networkDatasource.getFilms()
            .getResultOrThrow().films

        if (nwFilms.isNotEmpty()) {
            ioScope.launch {
                runCatching { filmsDao.clearInsert(nwFilms.toEntities()) }
                    .onFailure(Timber::e)
            }
        }

        val nwFilm = nwFilms.firstOrNull { it.id == id }
        if (nwFilm != null) {
            emit(Result.Success(nwFilm.toModel()))
            return@flow
        }

        emit(Result.Error(exception = EmptyException))

    }.catch {
        Timber.e(it)
        emit(Result.Error(it))
    }

    override suspend fun insert(item: Film) = filmsDao.insert(item.toEntity())
}
