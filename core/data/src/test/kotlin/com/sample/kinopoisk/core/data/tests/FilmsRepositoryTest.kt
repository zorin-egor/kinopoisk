package com.sample.kinopoisk.core.data.tests

import JvmUnitTestDevAssetManager
import com.sample.kinopoisk.core.common.result.Result
import com.sample.kinopoisk.core.data.repositories.films.FilmsRepository
import com.sample.kinopoisk.core.data.repositories.films.FilmsRepositoryImpl
import com.sample.kinopoisk.core.data.tests.dao.FilmsDaoTestImpl
import com.sample.kinopoisk.core.database.dao.FilmsDao
import com.sample.kinopoisk.core.network.NetworkDataSource
import com.sample.kinopoisk.core.network.dev.DevNetworkDataSource
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FilmsRepositoryTest {

    private val scope = TestScope(TestCoroutineScheduler())
    private val dispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    private lateinit var subject: FilmsRepository
    private lateinit var network: NetworkDataSource
    private lateinit var daoTest: FilmsDao

    @Before
    fun setup() {
        daoTest = FilmsDaoTestImpl()

        network = DevNetworkDataSource(
            dispatcher = dispatcher,
            networkJson = Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            },
            assets = JvmUnitTestDevAssetManager,
        )

        subject = FilmsRepositoryImpl(
            filmsDao = daoTest,
            ioScope = scope,
            networkDatasource = network,
        )
    }

    @Test
    fun getFilmsTest() = runTest(dispatcher) {
        val film = subject.getFilms().toList()
        assertEquals(Result.Loading, film[0])
        assertEquals(Result.Success::class.simpleName, film[1]::class.simpleName)
        assertEquals(17, (film[1] as Result.Success).data.size)
    }

}