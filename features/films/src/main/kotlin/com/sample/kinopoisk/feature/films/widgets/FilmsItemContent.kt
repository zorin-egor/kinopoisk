package com.sample.kinopoisk.feature.films.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.sample.kinopoisk.core.model.Film
import com.sample.kinopoisk.core.ui.R

@Composable
fun FilmsItemContent(
    item: Film,
    onItemClick: (Film) -> Unit,
    modifier: Modifier
) {
    var isSuccess by remember { mutableStateOf(false) }
    val imageLoader = rememberAsyncImagePainter(
        model = item.imageUrl,
        onSuccess = { isSuccess = true }
    )

    Column(
        modifier = modifier.clickable(
            interactionSource = null,
            indication = null,
            onClick = { onItemClick(item) }
        )
    ) {

        Box {
            if (!isSuccess) {
                Image(
                    painter = painterResource(R.drawable.ic_no_image),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.align(Alignment.Center)
                        .fillMaxWidth()
                        .height(222.dp)
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(size = 4.dp))
                        .background(color = MaterialTheme.colorScheme.surface)
                )
            }

            Image(
                painter = imageLoader,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(222.dp)
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(size = 4.dp))
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = item.localizedName,
            maxLines = 2,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.Start)
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }

}