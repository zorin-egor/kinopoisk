package com.sample.kinopoisk.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.sample.kinopoisk.R
import com.sample.kinopoisk.core.navigation.NavigationCommands
import com.sample.kinopoisk.core.navigation.NavigationViewModel
import com.sample.kinopoisk.feature.film_details.FilmDetailsFragment
import kotlinx.coroutines.launch

val FragmentActivity.navController: NavController
    get() = findNavController(R.id.mainNavigationHost)

fun FragmentActivity.collectNavigation(navViewModel: NavigationViewModel) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            navViewModel.navigation.collect {
                when(it) {
                    NavigationCommands.Back -> navController.popBackStack()
                    is NavigationCommands.ToFilmsDetails -> navController.navigate(
                        resId = R.id.filmDetailsScreen,
                        args = bundleOf(FilmDetailsFragment.KEY_ID to it.id)
                    )
                }
            }
        }
    }
}

