package com.sample.kinopoisk.core.domain.usecases

import com.sample.kinopoisk.core.common.di.Dispatcher
import com.sample.kinopoisk.core.common.di.Dispatchers
import com.sample.kinopoisk.core.common.result.Result
import com.sample.kinopoisk.core.data.repositories.films.FilmsRepository
import com.sample.kinopoisk.core.domain.roundTo
import com.sample.kinopoisk.core.model.Film
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map


class GetFilmByIdUseCase(
    private val repository: FilmsRepository,
    @Dispatcher(Dispatchers.IO) val dispatcher: CoroutineDispatcher,
) {

    operator fun invoke(id: Long): Flow<Result<Film>> = repository.getFilm(id)
        .map { item ->
            if (item is Result.Success) {
                Result.Success(item.data.copy(rating = item.data.rating?.roundTo))
            } else {
                item
            }
        }
        .flowOn(dispatcher)

}
