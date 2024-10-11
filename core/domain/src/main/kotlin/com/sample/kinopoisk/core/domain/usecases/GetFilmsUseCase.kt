package com.sample.kinopoisk.core.domain.usecases

import com.sample.kinopoisk.core.common.di.Dispatcher
import com.sample.kinopoisk.core.common.di.Dispatchers
import com.sample.kinopoisk.core.common.result.Result
import com.sample.kinopoisk.core.data.repositories.films.FilmsRepository
import com.sample.kinopoisk.core.model.Film
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


class GetFilmsUseCase(
    private val repository: FilmsRepository,
    @Dispatcher(Dispatchers.IO) val dispatcher: CoroutineDispatcher,
) {

    operator fun invoke(): Flow<Result<List<Film>>> = repository.getFilms()
        .flowOn(dispatcher)

}
