package com.sample.kinopoisk.feature.film_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.sample.kinopoisk.core.navigation.NavigationCommands
import com.sample.kinopoisk.core.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FilmDetailsFragment : Fragment() {

    companion object {
        const val KEY_ID = "KEY_ID"
    }

    private val id: Long
        get() = arguments?.getLong(KEY_ID)
            ?: throw IllegalStateException("Unknown state, id must be set.")

    private val navigation by activityViewModel<NavigationViewModel>()
    private val filmDetailsViewModel by viewModel<FilmDetailsViewModel> { parametersOf(id) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(
        context = requireContext()
    ).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
        setContent {
            FilmDetailsScreen(
                state = filmDetailsViewModel.uiState,
                onBackClick = { navigation.navigateTo(NavigationCommands.Back) },
                onShowSnackbar = { s1, s2 -> true },
            )
        }
    }

}