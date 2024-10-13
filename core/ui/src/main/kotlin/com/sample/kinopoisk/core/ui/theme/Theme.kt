package com.sample.kinopoisk.core.ui.theme

import androidx.annotation.VisibleForTesting
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@VisibleForTesting
val LightDefaultColorScheme = lightColorScheme(
    primary = Blue1,
    onPrimary = White1,
    secondary = Yellow1,
    onSecondary = Black,
    background = White1,
    onBackground = Black,
    surface = Grey1,
    onSurfaceVariant = Grey2,
    error = Red1,
    outline = Blue1,
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider {
        MaterialTheme(
            colorScheme = LightDefaultColorScheme,
            content = content,
        )
    }
}