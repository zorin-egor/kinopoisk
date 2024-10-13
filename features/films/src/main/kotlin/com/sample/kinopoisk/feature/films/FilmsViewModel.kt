package com.sample.kinopoisk.feature.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.kinopoisk.core.common.result.Result
import com.sample.kinopoisk.core.domain.usecases.GetFilmsUseCase
import com.sample.kinopoisk.core.model.FilmsAndGenres
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class FilmsViewModel(
    private val getFilmsUseCase: GetFilmsUseCase,
) : ViewModel() {

    private val _action = Channel<FilmsActions>(capacity = Channel.CONFLATED)

    val action: Flow<FilmsActions> = _action.receiveAsFlow()

    private val _checked = MutableStateFlow<GenreUi>(emptyGenre)

    private val _retry = MutableStateFlow<Boolean>(false)

    private var isColdLoad: Boolean = true

    val uiState: StateFlow<FilmsUiState> = _checked.combine(_retry) { genre, _ ->
        genre
    }.flatMapLatest {
        getFilmsUseCase(filter = if (it.isChecked) it.genre else "")
    }.mapNotNull { result ->
        when(result) {
            Result.Loading -> if (isColdLoad) {
                FilmsUiState.Loading
            } else {
                null
            }

            is Result.Error -> {
                _action.send(FilmsActions.ShowError(result.exception))
                return@mapNotNull null
            }

            is Result.Success -> {
                if (result.data.films.isEmpty()) {
                    FilmsUiState.Empty
                } else {
                    isColdLoad = false
                    result.data.toUiSuccess()
                }
            }
        }
    }.stateIn(scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = FilmsUiState.Loading
    )

    fun checkGenre(genre: GenreUi) {
        val isChecked = if (genre.genre == _checked.value.genre) !_checked.value.isChecked else true
        _checked.tryEmit(genre.copy(isChecked = isChecked))
    }

    fun retry() {
        _retry.tryEmit(!_retry.value)
    }

    private fun FilmsAndGenres.toUiSuccess() = FilmsUiState.Success(
        films = films.toImmutableList(),
        genres = genres.map { it.toGenreUi() }.toImmutableList()
    )

    private fun String.toGenreUi() = GenreUi(
        isChecked = this == _checked.value.genre && _checked.value.isChecked,
        genre = this
    )

}