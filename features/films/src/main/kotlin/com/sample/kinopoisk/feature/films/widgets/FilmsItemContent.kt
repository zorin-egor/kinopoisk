package com.sample.kinopoisk.feature.films.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.sample.kinopoisk.core.model.Film
import com.sample.kinopoisk.core.ui.icon.AppIcons

@Composable
fun FilmsItemContent(
    item: Film,
    onItemClick: (Film) -> Unit,
    modifier: Modifier
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

    Column(
        modifier = Modifier.size(width = 160.dp, height = 270.dp)
            .clickable(onClick = { onItemClick(item) })
    ) {

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(222.dp)
                .background(color = Color.Red)
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
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(size = 4.dp))
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = item.localizedName,
            maxLines = 2,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.align(Alignment.Start)
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }

}