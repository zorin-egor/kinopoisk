package com.sample.kinopoisk.feature.film_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.kinopoisk.core.common.result.Result
import com.sample.kinopoisk.core.domain.usecases.GetFilmByIdUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn

class FilmDetailsViewModel(
    private val getFilmByIdUseCase: GetFilmByIdUseCase,
    private val id: Long,
) : ViewModel() {

    private val _action = MutableSharedFlow<FilmDetailsActions>(replay = 1, extraBufferCapacity = 1)

    val action: SharedFlow<FilmDetailsActions> = _action.asSharedFlow()

    private val _retry = MutableStateFlow<Boolean>(false)

    val uiState: StateFlow<FilmDetailsUiState> = _retry.flatMapLatest {
            getFilmByIdUseCase(id = id)
        }.mapNotNull { result ->
            when(result) {
                Result.Loading -> FilmDetailsUiState.Loading
                is Result.Error -> {
                    _action.emit(FilmDetailsActions.ShowError(UnknownError()))
                    FilmDetailsUiState.Empty
                }
                is Result.Success -> FilmDetailsUiState.Success(result.data)
            }
        }.stateIn(scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = FilmDetailsUiState.Loading
        )

    fun retry() {
        _retry.tryEmit(!_retry.value)
        _action.tryEmit(FilmDetailsActions.None)
    }

}