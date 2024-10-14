package com.sample.kinopoisk.feature.films.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.sample.kinopoisk.feature.films.GenreUi

@Composable
fun GenreItemContent(
    item: GenreUi,
    onItemClick: (GenreUi) -> Unit,
    paddingHorizontal: Dp,
    modifier: Modifier = Modifier
) {
    val color = if (item.isChecked) MaterialTheme.colorScheme.secondary else Color.Transparent

    Button(
        onClick = { onItemClick(item) },
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RectangleShape,
        contentPadding = PaddingValues(horizontal = paddingHorizontal),
        modifier = modifier
    ) {
        Text(
            text = item.genre.capitalize(Locale.current),
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Start,
            maxLines = 1,
            letterSpacing = 0.1.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}