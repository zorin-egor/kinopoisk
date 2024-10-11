package com.sample.kinopoisk.core.data.repositories.films

import com.sample.kinopoisk.core.common.result.Result
import com.sample.kinopoisk.core.model.Film
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {

    fun getFilms(): Flow<Result<List<Film>>>

}