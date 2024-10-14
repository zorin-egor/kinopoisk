package com.sample.kinopoisk.core.data.repositories.films

import com.sample.kinopoisk.core.common.result.Result
import com.sample.kinopoisk.core.model.Film
import com.sample.kinopoisk.core.network.exceptions.EmptyException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class FilmsRepositoryTestImpl : FilmsRepository {

    private val items = MutableStateFlow(emptyList<Film>())

    var isDelayEnabled: Boolean = false

    override fun getFilms(): Flow<Result<List<Film>>> = items.map<List<Film>, Result<List<Film>>> {
        if (isDelayEnabled) delay(1000)
        Result.Success(it)
    }.onStart {
        emit(Result.Loading)
    }

    override fun getFilm(id: Long): Flow<Result<Film>> = items.map<List<Film>, Result<Film>> { flow ->
        if (isDelayEnabled) delay(1000)
        flow.firstOrNull { it.id == id }
            ?.let { Result.Success(it) }
            ?: Result.Error(EmptyException)
    }.onStart {
        emit(Result.Loading)
    }

    override suspend fun insert(item: Film) =
        items.update { flow -> (flow + item).distinctBy(Film::id) }

}