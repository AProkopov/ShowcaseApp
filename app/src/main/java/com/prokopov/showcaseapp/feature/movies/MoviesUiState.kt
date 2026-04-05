package com.prokopov.showcaseapp.feature.movies

sealed interface MoviesUiState {
    data object Loading : MoviesUiState

    data class Success(
        val movies: List<Movie>,
        val isLoadingMore: Boolean,
        val page: Int,
    ) : MoviesUiState

    data class Error(val message: String) : MoviesUiState
}
