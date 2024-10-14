package com.sample.kinopoisk.feature.films

import com.sample.kinopoisk.core.data.repositories.films.FilmsRepositoryTestImpl
import com.sample.kinopoisk.core.domain.usecases.GetFilmsUseCase
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


class FilmsViewModelTest {

    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val mainDispatcherRule = object : TestWatcher() {
        override fun starting(description: Description) = Dispatchers.setMain(testDispatcher)
        override fun finished(description: Description) = Dispatchers.resetMain()
    }

    private val repository = FilmsRepositoryTestImpl()

    private val useCase = GetFilmsUseCase(
        repository = repository,
        dispatcher = testDispatcher,
    )

    private lateinit var viewModel: FilmsViewModel

    @Before
    fun setup() {
        viewModel = FilmsViewModel(
            getFilmsUseCase = useCase,
        )
    }

    @Test
    fun emptyFilmsViewModelTest() = runTest {
        repository.isDelayEnabled = true
        val items = viewModel.uiState.take(2).toList()
        assertEquals(FilmsUiState.Loading, items[0])
        assertEquals(FilmsUiState.Empty, items[1])
    }

}