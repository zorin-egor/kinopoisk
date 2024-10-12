package com.sample.kinopoisk.feature.films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sample.kinopoisk.core.navigation.NavigationCommands
import com.sample.kinopoisk.core.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmsFragment : Fragment() {

    private val navigation by activityViewModels<NavigationViewModel>()
    private val filmsViewModel by viewModel<FilmsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(
        context = requireContext()
    ).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
        setContent {
            FilmsScreen(
                state = filmsViewModel.uiState,
                onItemClick = { id -> navigation.navigateTo(NavigationCommands.ToFilmsDetails(id)) },
                onShowSnackbar = { s1, s2 -> true },
            )
        }
    }

}