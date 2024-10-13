package com.sample.kinopoisk.feature.film_details.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.sample.kinopoisk.core.model.Film
import com.sample.kinopoisk.core.ui.component.AppTopBarColored
import com.sample.kinopoisk.core.ui.icon.AppIcons
import com.sample.kinopoisk.core.ui.theme.Blue1
import com.sample.kinopoisk.feature.film_details.R

@Composable
fun FilmDetailsItemContent(
    item: Film,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val imageLoader = rememberAsyncImagePainter(
        model = item.imageUrl,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )
    Column {
        AppTopBarColored(
            title = item.name,
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            navigationIcon = AppIcons.ArrowBack,
            onNavigationClick = onNavigationClick
        )

        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                if (isLoading || isError) {
                    Image(
                        imageVector = AppIcons.ArrowBack,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxSize()
                            .align(Alignment.Center)
                    )
                }

                Image(
                    painter = imageLoader,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .background(color = Color.Gray)
                        .align(Alignment.Center)
                        .size(width = 132.dp, height = 201.dp)
                        .clip(RoundedCornerShape(size = 4.dp))
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = item.localizedName,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )

            Text(
                text = "${item.genres.first()}, ${item.year} ${stringResource(R.string.feature_film_details_year)}",
                fontSize = 16.sp,
                maxLines = 1
            )

            item.rating?.let {
                runCatching {
                    val value = it.toString()
                    val title = stringResource(R.string.feature_film_details_kinopoisk)
                    val total = "$value $title"

                    val text = buildAnnotatedString {
                        append(total)
                        addStyle(
                            style = SpanStyle(
                                color = Blue1,
                                fontSize = 24.sp
                            ),
                            start = 0,
                            end = value.length
                        )
                        addStyle(
                            style = SpanStyle(
                                color = Blue1,
                                fontSize = 15.sp
                            ),
                            start = it.toString().length + 1,
                            end = total.length
                        )
                    }

                    Text(
                        text = text,
                        maxLines = 1
                    )
                }
            }

            item.description?.let {
                Text(
                    text = it,
                    fontSize = 14.sp
                )
            }
        }
    }
}