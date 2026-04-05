package com.prokopov.showcaseapp.feature.movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MoviesScreen(modifier: Modifier = Modifier) {
    val viewModel: MoviesViewModel = viewModel(factory = MoviesViewModel.Factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MoviesContent(
        uiState = uiState,
        onLoadMore = viewModel::loadNextPage,
        onRetry = viewModel::retry,
        modifier = modifier,
    )
}
