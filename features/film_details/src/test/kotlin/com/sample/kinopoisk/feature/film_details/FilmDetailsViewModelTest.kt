package com.sample.kinopoisk.feature.film_details

import com.sample.kinopoisk.core.data.repositories.films.FilmsRepositoryTestImpl
import com.sample.kinopoisk.core.domain.usecases.GetFilmByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.test.assertEquals


class FilmDetailsViewModelTest {

    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val mainDispatcherRule = object : TestWatcher() {
        override fun starting(description: Description) = Dispatchers.setMain(testDispatcher)
        override fun finished(description: Description) = Dispatchers.resetMain()
    }

    private val repository = FilmsRepositoryTestImpl()

    private val useCase = GetFilmByIdUseCase(
        repository = repository,
        dispatcher = testDispatcher,
    )

    private lateinit var viewModel: FilmDetailsViewModel

    @Before
    fun setup() {
        viewModel = FilmDetailsViewModel(
            getFilmByIdUseCase = useCase,
            id = 0
        )
    }

    @Test
    fun emptyFilmsViewModelTest() = runTest {
        repository.isDelayEnabled = true
        val items = viewModel.uiState.take(2).toList()
        assertEquals(FilmDetailsUiState.Loading, items[0])
        assertEquals(FilmDetailsUiState.Empty, items[1])
    }

}