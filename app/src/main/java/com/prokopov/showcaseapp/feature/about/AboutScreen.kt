package com.prokopov.showcaseapp.feature.about

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    val viewModel: AboutViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AboutContent(
        uiState = uiState,
        modifier = modifier,
    )
}
