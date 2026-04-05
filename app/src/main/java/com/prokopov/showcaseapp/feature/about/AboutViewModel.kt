package com.prokopov.showcaseapp.feature.about

import androidx.lifecycle.ViewModel
import com.prokopov.showcaseapp.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AboutViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<AboutUiState> = MutableStateFlow(createSuccessState())
    val uiState: StateFlow<AboutUiState> = _uiState.asStateFlow()

    private fun createSuccessState(): AboutUiState.Success =
        AboutUiState.Success(
            appName = APP_NAME,
            versionName = BuildConfig.VERSION_NAME,
            author = AUTHOR,
            description = DESCRIPTION,
            githubUrl = GITHUB_URL,
        )

    companion object {
        private const val APP_NAME = "ShowcaseApp"
        private const val AUTHOR = "Anton Prokopov"
        private const val DESCRIPTION = "Android app demonstrating best engineering practices"
        private const val GITHUB_URL = "https://github.com/AProkopov/ShowcaseApp"
    }
}
