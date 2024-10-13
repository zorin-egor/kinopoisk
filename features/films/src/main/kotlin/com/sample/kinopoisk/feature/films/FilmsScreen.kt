package com.sample.kinopoisk.feature.films

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.kinopoisk.core.model.Film
import com.sample.kinopoisk.core.ui.component.AppTopBarColored
import com.sample.kinopoisk.core.ui.component.getLazyListHeight
import com.sample.kinopoisk.feature.films.widgets.FilmsItemContent
import com.sample.kinopoisk.feature.films.widgets.GenreItemContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private val lazyListPadding = 8.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmsScreen(
    state: StateFlow<FilmsUiState>,
    onGenreClick: (GenreUi) -> Unit,
    onFilmClick: (Film) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val uiState = state.collectAsStateWithLifecycle()

    when(val state = uiState.value) {
        FilmsUiState.Empty -> Text("Empty")
        FilmsUiState.Loading -> Text("Loading")
        is FilmsUiState.Success -> {
            Column(modifier = Modifier.fillMaxSize()) {
                AppTopBarColored(
                    title = stringResource(R.string.feature_films_title),
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                )

                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = stringResource(R.string.feature_films_genres),
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                            .padding(lazyListPadding)
                    )

                    state.genres.forEach { item ->
                        GenreItemContent(
                            item = item,
                            onItemClick = onGenreClick,
                            paddingHorizontal = lazyListPadding,
                            modifier = Modifier.fillMaxWidth()
                                .height(40.dp)
                        )
                    }

                    Text(
                        text = stringResource(R.string.feature_films_films),
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                            .padding(lazyListPadding)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(lazyListPadding),
                        verticalArrangement = Arrangement.spacedBy(lazyListPadding),
                        horizontalArrangement = Arrangement.spacedBy(lazyListPadding),
                        modifier = Modifier.fillMaxWidth()
                            .height(getLazyListHeight(
                                size = state.films.size,
                                columns = 2,
                                itemHeight = 270.dp,
                                padding = lazyListPadding
                            )),
                        userScrollEnabled = false
                    ) {
                        items(
                            items = state.films,
                            key = { it.id }
                        ) { item ->
                            FilmsItemContent(
                                item = item,
                                onItemClick = onFilmClick,
                                modifier = Modifier
                            )
                        }
                    }

                    Spacer(
                        Modifier.windowInsetsBottomHeight(
                            WindowInsets.safeContent
                        )
                    )
                }
            }
        }
    }
}

@Preview("FilmsScreen")
@Composable
private fun FilmsScreenPreview() {
    FilmsScreen(
        state = MutableStateFlow(FilmsUiState.Loading).asStateFlow(),
        onGenreClick = {},
        onFilmClick = {},
        onShowSnackbar = {_, _ -> true },
    )
}

