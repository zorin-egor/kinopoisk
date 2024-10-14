package com.sample.kinopoisk.core.network.tests

import JvmUnitTestDevAssetManager
import com.sample.kinopoisk.core.network.dev.DevNetworkDataSource
import com.sample.kinopoisk.core.network.models.NetworkFilm
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DevNetworkDataSourceTest {

    private lateinit var subject: DevNetworkDataSource

    private val testDispatcher = StandardTestDispatcher()

    private val firstFilm: NetworkFilm = NetworkFilm(
        description = "Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.",
        genres = listOf("драма"),
        id = 326,
        imageUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_326.jpg",
        localizedName = "Побег из Шоушенка",
        name = "The Shawshank Redemption",
        rating = 9.196,
        year = 1994
    )

    private val lastFilm: NetworkFilm = NetworkFilm(
        description = "Два друга мечтают о полете в космос и делают все для достижения своей цели.",
        genres = emptyList(),
        id = 841340,
        imageUrl = null,
        localizedName = "Мы не можем жить без космоса",
        name = "Мы не можем жить без космоса",
        rating = null,
        year = 2014
    )


    @Before
    fun setUp() {
        subject = DevNetworkDataSource(
            networkJson = Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            },
            dispatcher = testDispatcher,
            assets = JvmUnitTestDevAssetManager,
        )
    }

    @Test
    fun getUserTest() = runTest(testDispatcher) {
        val films = subject.getFilms().body()
        assertEquals(firstFilm, films?.films?.first())
        assertEquals(lastFilm, films?.films?.last())
    }

}
