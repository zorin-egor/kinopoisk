package com.sample.kinopoisk.core.common.di

import com.sample.kinopoisk.core.navigation.NavigationViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class NavigationModule {

    @Singleton
    fun provideNavigationViewModel() = NavigationViewModel()

}


