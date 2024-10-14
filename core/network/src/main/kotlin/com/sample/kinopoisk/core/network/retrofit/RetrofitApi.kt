package com.sample.kinopoisk.core.network.retrofit

import com.sample.kinopoisk.core.network.models.NetworkFilms
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitApi {

    @GET("sequeniatesttask/films.json")
    suspend fun getFilms(): Response<NetworkFilms>

}