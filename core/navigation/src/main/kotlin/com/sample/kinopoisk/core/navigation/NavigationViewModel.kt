package com.sample.kinopoisk.core.navigation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class NavigationViewModel : ViewModel() {

    private val _navigation = Channel<NavigationCommands>(capacity = Channel.CONFLATED)
    val navigation: Flow<NavigationCommands> = _navigation.receiveAsFlow()

    fun navigateTo(value: NavigationCommands) {
        _navigation.trySend(value)
    }

}