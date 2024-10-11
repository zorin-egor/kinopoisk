package com.sample.kinopoisk.core.data.repositories.films

import com.sample.kinopoisk.core.common.result.Result
import com.sample.kinopoisk.core.common.result.asResult
import com.sample.kinopoisk.core.data.mapper.toModels
import com.sample.kinopoisk.core.database.dao.FilmsDao
import com.sample.kinopoisk.core.model.Film
import com.sample.kinopoisk.core.network.NetworkDataSource
import com.sample.kinopoisk.core.network.ext.getResultOrThrow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


internal class FilmsRepositoryImpl(
    private val filmsDao: FilmsDao,
    private val networkDatasource: NetworkDataSource,
) : FilmsRepository {

    override fun getFilms(): Flow<Result<List<Film>>> = flow<List<Film>> {
        emit(
            networkDatasource.getFilms()
                .getResultOrThrow().films
                .toModels()
        )
    }.asResult()

}
