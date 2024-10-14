package com.sample.kinopoisk.feature.film_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.kinopoisk.core.ui.component.AppTopBarColored
import com.sample.kinopoisk.core.ui.component.CircularContent
import com.sample.kinopoisk.core.ui.component.SimplePlaceholderContent
import com.sample.kinopoisk.core.ui.icon.AppIcons
import com.sample.kinopoisk.feature.film_details.widgets.FilmDetailsItemContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.sample.kinopoisk.core.ui.R as CoreUiR

@Composable
fun FilmDetailsScreen(
    state: StateFlow<FilmDetailsUiState>,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState = state.collectAsStateWithLifecycle()
    val initialToolbarHeader = stringResource(R.string.feature_film_details_title)
    var toolbarHeader by remember { mutableStateOf("") }

    Column(modifier = modifier) {

            AppTopBarColored(
                title = toolbarHeader,
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                navigationIcon = AppIcons.ArrowBack,
                onNavigationClick = onBackClick
            )

            when(val item = uiState.value) {
                FilmDetailsUiState.Loading -> CircularContent()
                FilmDetailsUiState.Empty -> {
                    toolbarHeader = initialToolbarHeader
                    SimplePlaceholderContent(
                        image = AppIcons.Empty,
                        header = stringResource(CoreUiR.string.empty_placeholder_header),
                        title = stringResource(CoreUiR.string.empty_placeholder_title),
                        imageContentDescription = stringResource(CoreUiR.string.empty_placeholder_header)
                    )
                }
                is FilmDetailsUiState.Success -> {
                    toolbarHeader = item.film.name
                    FilmDetailsItemContent(
                        item = item.film,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
}

@Preview("FilmDetailsScreen")
@Composable
private fun FilmsScreenPreview() {
    FilmDetailsScreen(
        state = MutableStateFlow(FilmDetailsUiState.Loading).asStateFlow(),
        onBackClick = {},
    )
}


