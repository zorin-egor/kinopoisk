package com.sample.kinopoisk.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sample.kinopoisk.core.ui.icon.AppIcons

@Composable
fun SimplePlaceholderContent(
    header: String,
    image: ImageVector,
    modifier: Modifier = Modifier,
    title: String? = null,
    imageContentDescription: String? = null
) {
    val materialTheme = MaterialTheme.colorScheme

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            imageVector = image,
            contentDescription = imageContentDescription,
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
                .alpha(0.7f),
            alignment = Alignment.Center,
            colorFilter = ColorFilter.tint(materialTheme.primary)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = header,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .alpha(0.5f),
        )

        if (title != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterHorizontally).alpha(0.5f)
            )
        }
    }
}

@Preview
@Composable
fun SearchPlaceholderContentPreview() {
    SimplePlaceholderContent(
        header = "Header",
        title = "Title",
        image = AppIcons.Empty,
        imageContentDescription = "Description"
    )
}