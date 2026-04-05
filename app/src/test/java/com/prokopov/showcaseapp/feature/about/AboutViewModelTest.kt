package com.prokopov.showcaseapp.feature.about

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AboutViewModelTest {
    private val viewModel = AboutViewModel()

    @Test
    fun `uiState has correct app name`() {
        assertEquals("ShowcaseApp", viewModel.uiState.value.appName)
    }

    @Test
    fun `uiState has correct author`() {
        assertEquals("Anton Prokopov", viewModel.uiState.value.author)
    }

    @Test
    fun `uiState has correct description`() {
        assertEquals(
            "Android app demonstrating best engineering practices",
            viewModel.uiState.value.description,
        )
    }

    @Test
    fun `uiState has correct github url`() {
        assertEquals(
            "https://github.com/AProkopov/ShowcaseApp",
            viewModel.uiState.value.githubUrl,
        )
    }

    @Test
    fun `uiState has non-empty version name`() {
        assertTrue(viewModel.uiState.value.versionName.isNotEmpty())
    }
}
