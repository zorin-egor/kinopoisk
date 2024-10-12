package com.sample.kinopoisk.feature.films

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow

@Composable
fun FilmsScreen(
    state: StateFlow<FilmsUiState>,
    onItemClick: (Long) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val uiState = state.collectAsStateWithLifecycle()
    when(val state = uiState.value) {
        FilmsUiState.Empty -> Text("Empty")
        FilmsUiState.Loading -> Text("Loading")
        is FilmsUiState.Success -> Text(
            text = state.films.joinToString(separator = "\n") { it.name },
            modifier = Modifier.clickable {
                onItemClick(state.films.random().id)
            }
        )
    }
}

