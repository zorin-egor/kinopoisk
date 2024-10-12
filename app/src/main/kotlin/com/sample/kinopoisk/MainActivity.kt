package com.sample.kinopoisk

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.sample.kinopoisk.core.navigation.NavigationViewModel
import com.sample.kinopoisk.navigation.collectNavigation

class MainActivity : FragmentActivity() {

    private val navViewModel by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        collectNavigation(navViewModel)
    }
}