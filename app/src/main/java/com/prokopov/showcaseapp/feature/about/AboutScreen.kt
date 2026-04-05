package com.prokopov.showcaseapp.feature.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    val viewModel: AboutViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is AboutUiState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier.fillMaxSize(),
            ) {
                CircularProgressIndicator()
            }
        }
        is AboutUiState.Success -> {
            AboutContent(
                uiState = state,
                modifier = modifier,
            )
        }
    }
}
