package com.sample.kinopoisk.core.network.dev

import JvmUnitTestDevAssetManager
import com.sample.kinopoisk.core.common.di.Dispatcher
import com.sample.kinopoisk.core.common.di.Dispatchers

import com.sample.kinopoisk.core.network.NetworkDataSource
import com.sample.kinopoisk.core.network.models.NetworkFilms
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import retrofit2.Response
import java.io.InputStream


class DevNetworkDataSource(
    private val networkJson: Json,
    @Dispatcher(Dispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val assets: DevAssetManager = JvmUnitTestDevAssetManager,
) : NetworkDataSource {

    companion object {
        private const val KINOPOISK_MOVIES = "kinopoisk_get_films.json"
    }

    override suspend fun getFilms(): Response<NetworkFilms> {
        return withContext(dispatcher) {
            assets.open(KINOPOISK_MOVIES).use<InputStream, NetworkFilms>(networkJson::decodeFromStream)
                .let { Response.success(it) }
        }
    }

}
