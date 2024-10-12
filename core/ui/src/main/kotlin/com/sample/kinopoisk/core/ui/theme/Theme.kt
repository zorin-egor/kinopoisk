package com.sample.kinopoisk.core.ui.theme

import androidx.annotation.VisibleForTesting
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@VisibleForTesting
val LightDefaultColorScheme = lightColorScheme(
    primary = Blue1,
    secondary = Blue1,
    tertiary = Blue1,
    background = White1,
    surface = White1,
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
            typography = Typography,
            content = content,
        )
    }
}