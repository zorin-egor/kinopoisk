package com.sample.kinopoisk.core.navigation

sealed interface NavigationCommands {
    data object Back : NavigationCommands
    data class ToFilmsDetails(val id: Long) : NavigationCommands
}