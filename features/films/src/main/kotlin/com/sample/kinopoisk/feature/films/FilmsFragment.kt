package com.sample.kinopoisk.feature.films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.kinopoisk.core.navigation.NavigationCommands
import com.sample.kinopoisk.core.navigation.NavigationViewModel
import com.sample.kinopoisk.core.network.exceptions.NetworkException
import com.sample.kinopoisk.core.ui.theme.AppTheme
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.sample.kinopoisk.core.ui.R as CoreUiR

class FilmsFragment : Fragment() {

    private val navigation by activityViewModel<NavigationViewModel>()
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

            val snackbarHostState = remember { SnackbarHostState() }
            val actionsState by filmsViewModel.action.collectAsStateWithLifecycle(null)

            when(val action = actionsState) {
                is FilmsActions.ShowError -> {
                    LaunchedEffect(actionsState) {
                        val error = if (action.error is NetworkException) {
                            CoreUiR.string.error_networking
                        } else {
                            CoreUiR.string.error_unknown
                        }

                        val snackBarAction = snackbarHostState.showSnackbar(
                            message = getString(error),
                            actionLabel = getString(CoreUiR.string.common_retry),
                            duration = SnackbarDuration.Indefinite,
                        )

                        if (snackBarAction == SnackbarResult.ActionPerformed) {
                            filmsViewModel.retry()
                        }
                    }
                }
                else -> {}
            }

            AppTheme {

                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(snackbarHostState) { data ->
                            Snackbar(
                                actionColor = MaterialTheme.colorScheme.secondary,
                                snackbarData = data
                            )
                        }
                    },
                ) {

                    FilmsScreen(
                            state = filmsViewModel.uiState,
                            onGenreClick = { genre -> filmsViewModel.checkGenre(genre) },
                            onFilmClick = { item -> navigation.navigateTo(NavigationCommands.ToFilmsDetails(item.id)) },
                            modifier = Modifier.fillMaxSize()
                                .padding(it)
                                .consumeWindowInsets(it)
                        )
                    }
            }
        }
    }

}