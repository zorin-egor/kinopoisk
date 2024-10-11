package com.sample.kinopoisk.core.network

import com.sample.kinopoisk.core.network.models.NetworkFilms
import retrofit2.Response

interface NetworkDataSource {

    suspend fun getFilms(): Response<NetworkFilms>

}