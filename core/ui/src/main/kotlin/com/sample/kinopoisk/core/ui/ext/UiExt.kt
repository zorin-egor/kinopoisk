package com.sample.kinopoisk.core.ui.ext

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
val windowSizeClass: WindowSizeClass
    @Composable get() {
        val config = LocalConfiguration.current
        return WindowSizeClass.calculateFromSize(DpSize(width = config.screenWidthDp.dp, height = config.screenHeightDp.dp))
    }

val shouldShowBottomBar: Boolean
    @Composable get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

inline fun <T> Flow<T>.flowLifecycle(lifecycle: LifecycleOwner, state: Lifecycle.State, crossinline onEach: (T) -> Unit) {
    flowWithLifecycle(lifecycle.lifecycle, state)
        .onEach { onEach(it) }
        .launchIn(lifecycle.lifecycleScope)
}

inline fun <T> Flow<T>.flowLifecycle(lifecycle: LifecycleOwner, crossinline onEach: (T) -> Unit) {
    flowLifecycle(lifecycle, Lifecycle.State.STARTED, onEach)
}