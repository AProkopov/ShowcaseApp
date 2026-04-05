package com.prokopov.showcaseapp.feature.about

sealed interface AboutUiState {
    data object Loading : AboutUiState

    data class Success(
        val appName: String,
        val versionName: String,
        val author: String,
        val description: String,
        val githubUrl: String,
    ) : AboutUiState
}
