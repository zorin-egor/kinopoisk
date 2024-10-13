package com.sample.kinopoisk.feature.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.kinopoisk.core.common.result.Result
import com.sample.kinopoisk.core.domain.usecases.GetFilmsUseCase
import com.sample.kinopoisk.core.model.FilmsAndGenres
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn

class FilmsViewModel(
    private val getFilmsUseCase: GetFilmsUseCase,
) : ViewModel() {

    private val _action = MutableSharedFlow<FilmsActions>(replay = 0, extraBufferCapacity = 1)

    val action: SharedFlow<FilmsActions> = _action.asSharedFlow()

    private val _checked = MutableStateFlow<GenreUi>(emptyGenre)

    val uiState: StateFlow<FilmsUiState> = _checked.flatMapLatest {
            getFilmsUseCase(filter = if (it.isChecked) it.genre else "")
        }.mapNotNull { result ->
            when(result) {
                Result.Loading -> return@mapNotNull null
                is Result.Error -> {
                    _action.emit(FilmsActions.ShowError(UnknownError()))
                    return@mapNotNull null
                }
                is Result.Success -> if (result.data.films.isEmpty()) {
                    FilmsUiState.Empty
                } else {
                    result.data.toUiSuccess()
                }
            }
        }.stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FilmsUiState.Loading
        )

    fun checkGenre(genre: GenreUi) {
        val isChecked = if (genre.genre == _checked.value.genre) !genre.isChecked else true
        _checked.tryEmit(genre.copy(isChecked = isChecked))
    }

    private fun FilmsAndGenres.toUiSuccess() = FilmsUiState.Success(
        films = films,
        genres = genres.map { it.toGenreUi() }
    )

    private fun String.toGenreUi() = GenreUi(
        isChecked = this == _checked.value.genre && _checked.value.isChecked,
        genre = this
    )

}