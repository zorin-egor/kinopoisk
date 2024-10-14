package com.sample.kinopoisk.core.domain

import com.sample.kinopoisk.core.common.result.Result
import com.sample.kinopoisk.core.data.repositories.films.FilmsRepositoryTestImpl
import com.sample.kinopoisk.core.domain.usecases.GetFilmByIdUseCase
import com.sample.kinopoisk.core.model.Film
import com.sample.kinopoisk.core.network.exceptions.EmptyException
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals


class GetFilmByIdUseCaseTest {

    private val dispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    private val repository = FilmsRepositoryTestImpl()

    private val userCase = GetFilmByIdUseCase(
        repository = repository,
        dispatcher = dispatcher,
    )

    @Test
    fun getFilmByIdEmptyDataTest() = runTest(dispatcher) {
        val result = userCase(-1).take(2).toList()
        assert(result.size == 2)
        assertEquals(EmptyException, (result[1] as Result.Error).exception)
    }

    @Test
    fun getFilmsByGenresTest() = runTest(dispatcher) {
        repeat(21) {
            repository.insert(getFilm(it.toLong()))
        }

        val result1 = userCase(10).take(2).toList()
        val success1 = (result1[1] as Result.Success<Film>).data
        assert(result1.size == 2)
        assertEquals(10, success1.id)
    }

}