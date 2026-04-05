package com.prokopov.showcaseapp.feature.about

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AboutViewModelTest {
    private val viewModel = AboutViewModel()

    @Test
    fun `uiState emits Success with correct app name`() {
        val state = viewModel.uiState.value
        assertTrue(state is AboutUiState.Success)
        assertEquals("ShowcaseApp", (state as AboutUiState.Success).appName)
    }

    @Test
    fun `uiState emits Success with correct author`() {
        val state = viewModel.uiState.value
        assertTrue(state is AboutUiState.Success)
        assertEquals("Anton Prokopov", (state as AboutUiState.Success).author)
    }

    @Test
    fun `uiState emits Success with correct description`() {
        val state = viewModel.uiState.value
        assertTrue(state is AboutUiState.Success)
        assertEquals(
            "Android app demonstrating best engineering practices",
            (state as AboutUiState.Success).description,
        )
    }

    @Test
    fun `uiState emits Success with correct github url`() {
        val state = viewModel.uiState.value
        assertTrue(state is AboutUiState.Success)
        assertEquals(
            "https://github.com/AProkopov/ShowcaseApp",
            (state as AboutUiState.Success).githubUrl,
        )
    }

    @Test
    fun `uiState emits Success with non-empty version name`() {
        val state = viewModel.uiState.value
        assertTrue(state is AboutUiState.Success)
        assertTrue((state as AboutUiState.Success).versionName.isNotEmpty())
    }
}
