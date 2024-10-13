package com.sample.kinopoisk

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import com.sample.kinopoisk.core.navigation.NavigationViewModel
import com.sample.kinopoisk.navigation.collectNavigation
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : FragmentActivity() {

    private val navViewModel by viewModel<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        collectNavigation(navViewModel)
    }
}