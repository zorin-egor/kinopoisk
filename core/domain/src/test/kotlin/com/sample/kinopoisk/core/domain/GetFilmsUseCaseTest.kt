package com.sample.kinopoisk.core.domain

import com.sample.kinopoisk.core.common.result.Result
import com.sample.kinopoisk.core.data.repositories.films.FilmsRepositoryTestImpl
import com.sample.kinopoisk.core.domain.usecases.GetFilmsUseCase
import com.sample.kinopoisk.core.model.FilmsAndGenres
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals


class GetFilmsUseCaseTest {

    private val dispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    private val repository = FilmsRepositoryTestImpl()

    private val userCase = GetFilmsUseCase(
        repository = repository,
        dispatcher = dispatcher,
    )

    @Test
    fun getFilmsEmptyDataTest() = runTest(dispatcher) {
        val result = userCase().take(2).toList()
        assert(result.size == 2)
        assertEquals(FilmsAndGenres(
            films = emptyList(),
            genres = emptySet<String>()
        ), (result[1] as Result.Success<FilmsAndGenres>).data)
    }

    @Test
    fun getFilmsByGenresTest() = runTest(dispatcher) {
        repeat(21) {
            repository.insert(getFilm(it.toLong()))
        }

        val result1 = userCase().take(2).toList()
        val success1 = (result1[1] as Result.Success<FilmsAndGenres>).data
        assert(result1.size == 2)
        assertEquals(3, success1.genres.size)
        assertEquals(21, success1.films.size)

        val result2 = userCase(filter = "genre1").take(2).toList()
        val success2 = (result2[1] as Result.Success<FilmsAndGenres>).data
        assert(result2.size == 2)
        assertEquals(3, success2.genres.size)
        assertEquals(7, success2.films.size)
    }

}