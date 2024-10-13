package com.sample.kinopoisk.feature.films.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sample.kinopoisk.core.ui.theme.Yellow1
import com.sample.kinopoisk.feature.films.GenreUi

@Composable
fun GenreItemContent(
    item: GenreUi,
    onItemClick: (GenreUi) -> Unit,
    paddingHorizontal: Dp,
    modifier: Modifier = Modifier
) {
    val color = if (item.isChecked) Yellow1 else Color.Transparent

    Button(
        onClick = { onItemClick(item) },
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RectangleShape,
        contentPadding = PaddingValues(paddingHorizontal),
        modifier = modifier
    ) {
        Text(
            text = item.genre,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
                .padding(0.dp)
        )
    }
}