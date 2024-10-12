package com.sample.kinopoisk.feature.film_details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow

@Composable
fun FilmDetailsScreen(
    state: StateFlow<FilmDetailsUiState>,
    onItemClick: (Long) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val uiState = state.collectAsStateWithLifecycle()
    when(val state = uiState.value) {
        FilmDetailsUiState.Empty -> Text("Empty")
        FilmDetailsUiState.Loading -> Text("Loading")
        is FilmDetailsUiState.Success -> Text(state.users.localizedName)
    }

}

