package com.sample.kinopoisk.core.domain.usecases

import com.sample.kinopoisk.core.common.di.Dispatcher
import com.sample.kinopoisk.core.common.di.Dispatchers
import com.sample.kinopoisk.core.common.result.Result
import com.sample.kinopoisk.core.data.repositories.films.FilmsRepository
import com.sample.kinopoisk.core.model.Film
import com.sample.kinopoisk.core.model.FilmsAndGenres
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.TreeSet


class GetFilmsUseCase(
    private val repository: FilmsRepository,
    @Dispatcher(Dispatchers.IO) val dispatcher: CoroutineDispatcher,
) {

    operator fun invoke(filter: String? = null): Flow<Result<FilmsAndGenres>> = repository.
    getFilms()
        .map { result ->
            when(result) {
                Result.Loading -> Result.Loading
                is Result.Error -> result
                is Result.Success -> {
                    val genres = mapToGenresAndFilms(result.data)
                    val filtered = filterByGenres(result.data, filter)
                    Result.Success(FilmsAndGenres(
                        films = filtered,
                        genres = genres
                    ))
                }
            }
        }.flowOn(dispatcher)

    private fun filterByGenres(items: List<Film>, genre: String?): List<Film> = when {
        genre.isNullOrEmpty() -> items
        else -> items.filter { film -> film.genres.find { it == genre } != null }
    }.sortedBy { it.localizedName }

    private fun mapToGenresAndFilms(items: List<Film>): Set<String> =
        TreeSet<String> { o1, o2 -> o1.compareTo(o2) }.apply {
            items.forEach { film -> addAll(film.genres) }
            remove("")
        }

}
