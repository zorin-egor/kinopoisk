package com.sample.kinopoisk.feature.film_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.kinopoisk.core.common.result.Result
import com.sample.kinopoisk.core.domain.usecases.GetFilmByIdUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn

class FilmDetailsViewModel(
    private val getFilmByIdUseCase: GetFilmByIdUseCase,
    private val id: Long,
) : ViewModel() {

    private val _action = MutableSharedFlow<FilmDetailsActions>(replay = 0, extraBufferCapacity = 1)

    val action: SharedFlow<FilmDetailsActions> = _action.asSharedFlow()

    val uiState: StateFlow<FilmDetailsUiState> = getFilmByIdUseCase(id = id)
        .mapNotNull { result ->
            when(result) {
                Result.Loading -> return@mapNotNull null
                is Result.Error -> {
                    _action.emit(FilmDetailsActions.ShowError(UnknownError()))
                    FilmDetailsUiState.Empty
                }
                is Result.Success -> FilmDetailsUiState.Success(result.data)
            }
        }.stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FilmDetailsUiState.Loading
        )

}