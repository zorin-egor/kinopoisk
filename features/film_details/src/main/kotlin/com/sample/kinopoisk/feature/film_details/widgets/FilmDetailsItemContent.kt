package com.sample.kinopoisk.feature.film_details.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.sample.kinopoisk.core.model.Film
import com.sample.kinopoisk.feature.film_details.R
import com.sample.kinopoisk.core.ui.R as CoreUiR


@Composable
fun FilmDetailsItemContent(
    item: Film,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    var scaleOnSuccess by remember { mutableStateOf(ContentScale.Inside) }
    val imageLoader = rememberAsyncImagePainter(
        model = item.imageUrl,
        placeholder = null,
        error = painterResource(CoreUiR.drawable.ic_no_image),
        onSuccess = { scaleOnSuccess = ContentScale.Crop }
    )

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Image(
            painter = imageLoader,
            contentDescription = null,
            contentScale = scaleOnSuccess,
            modifier = Modifier
                .size(width = 132.dp, height = 201.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(size = 4.dp))
                .background(color = Color.Gray)
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = item.localizedName,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 32.sp,
            letterSpacing = 0.1.sp
        )

        Spacer(Modifier.height(8.dp))

        item.genres.firstOrNull()?.let {
            Text(
                text = "${it}, ${item.year} ${stringResource(R.string.feature_film_details_year)}",
                fontSize = 16.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.1.sp
            )
        }

        Spacer(Modifier.height(10.dp))

        item.rating?.let {
            runCatching {
                val value = it.toString()
                val title = stringResource(R.string.feature_film_details_kinopoisk)
                val total = "$value $title"

                val text = buildAnnotatedString {
                    append(total)
                    addStyle(
                        style = SpanStyle(
                            color = primaryColor,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        start = 0,
                        end = value.length
                    )
                    addStyle(
                        style = SpanStyle(
                            color = primaryColor,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        start = it.toString().length + 1,
                        end = total.length
                    )
                }

                Text(
                    text = text,
                    maxLines = 1,
                    letterSpacing = 0.1.sp
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        item.description?.let {
            Text(
                text = it,
                fontSize = 14.sp,
                letterSpacing = 0.1.sp,
                lineHeight = 20.sp
            )
        }
    }
}