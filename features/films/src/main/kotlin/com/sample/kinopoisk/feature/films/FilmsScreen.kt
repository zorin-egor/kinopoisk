package com.sample.kinopoisk.feature.films

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.kinopoisk.core.model.Film
import com.sample.kinopoisk.core.ui.component.AppTopBarColored
import com.sample.kinopoisk.core.ui.component.CircularContent
import com.sample.kinopoisk.core.ui.component.SimplePlaceholderContent
import com.sample.kinopoisk.core.ui.component.getLazyListHeight
import com.sample.kinopoisk.core.ui.icon.AppIcons
import com.sample.kinopoisk.core.ui.theme.Yellow1
import com.sample.kinopoisk.feature.films.widgets.FilmsItemContent
import com.sample.kinopoisk.feature.films.widgets.GenreItemContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

private val lazyListCellsHorizontalPadding = 8.dp
private val lazyListCellsVerticalPadding = 16.dp
private val lazyListContentPadding = 16.dp

@Composable
fun FilmsScreen(
    state: StateFlow<FilmsUiState>,
    onGenreClick: (GenreUi) -> Unit,
    onFilmClick: (Film) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState = state.collectAsStateWithLifecycle()

    Column(modifier = modifier) {

        AppTopBarColored(
            title = stringResource(R.string.feature_films_title),
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        )

        when(val item = uiState.value) {
            FilmsUiState.Loading -> CircularContent(color = Yellow1)
            FilmsUiState.Empty -> SimplePlaceholderContent(
                image = AppIcons.Empty,
                header = stringResource(com.sample.kinopoisk.core.ui.R.string.empty_placeholder_header),
                title = stringResource(com.sample.kinopoisk.core.ui.R.string.empty_placeholder_title),
                imageContentDescription = stringResource(com.sample.kinopoisk.core.ui.R.string.empty_placeholder_header)
            )
            is FilmsUiState.Success -> {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                    Spacer(Modifier.height(8.dp))

                    Box(modifier = Modifier.height(40.dp)) {
                        Text(
                            text = stringResource(R.string.feature_films_genres),
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.wrapContentWidth()
                                .align(Alignment.CenterStart)
                                .padding(horizontal = lazyListContentPadding)
                        )
                    }

                    item.genres.forEach { item ->
                        GenreItemContent(
                            item = item,
                            onItemClick = onGenreClick,
                            paddingHorizontal = lazyListContentPadding,
                            modifier = Modifier.fillMaxWidth()
                                .height(40.dp)
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Box(modifier = Modifier.height(40.dp)) {
                        Text(
                            text = stringResource(R.string.feature_films_films),
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.wrapContentWidth()
                                .align(Alignment.CenterStart)
                                .padding(horizontal = lazyListContentPadding)
                        )
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(lazyListContentPadding),
                        verticalArrangement = Arrangement.spacedBy(lazyListCellsVerticalPadding),
                        horizontalArrangement = Arrangement.spacedBy(lazyListCellsHorizontalPadding),
                        modifier = Modifier.fillMaxWidth()
                            .height(
                                getLazyListHeight(
                                    size = item.films.size,
                                    columns = 2,
                                    itemHeight = 270.dp,
                                    paddingCells = lazyListCellsVerticalPadding,
                                    paddingContent = lazyListContentPadding
                                )
                            ),
                        userScrollEnabled = false
                    ) {
                        items(
                            items = item.films,
                            key = { it.id }
                        ) { item ->
                            Timber.d("Items")
                            FilmsItemContent(
                                item = item,
                                onItemClick = onFilmClick,
                                modifier = Modifier.fillMaxWidth()
                                    .height(270.dp)
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
    )
}

