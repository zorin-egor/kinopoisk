package com.sample.kinopoisk.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircularContent(
    color: Color = MaterialTheme.colorScheme.secondary
) {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            color = color,
            modifier = Modifier.size(50.dp).align(Alignment.Center)
        )
    }
}

@Preview("Search text field")
@Composable
fun CircularContentPreview() {
    CircularContent()
}