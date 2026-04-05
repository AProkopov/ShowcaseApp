package com.prokopov.showcaseapp.feature.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesViewModel(
    private val repository: MovieRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<MoviesUiState> =
        MutableStateFlow(MoviesUiState.Loading)
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    private var currentPage = 0
    private var totalPages = Int.MAX_VALUE

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        val nextPage = currentPage + 1
        if (nextPage > totalPages) return

        val currentState = _uiState.value
        if (currentState is MoviesUiState.Success && currentState.isLoadingMore) return
        if (currentState is MoviesUiState.Loading && currentPage > 0) return

        if (currentState is MoviesUiState.Success) {
            _uiState.value = currentState.copy(isLoadingMore = true)
        }

        viewModelScope.launch {
            repository.getTopRatedMovies(nextPage)
                .onSuccess { response -> handleSuccess(response, currentState) }
                .onFailure { error -> handleFailure(error, currentState) }
        }
    }

    private fun handleSuccess(
        response: TopRatedMoviesResponse,
        previousState: MoviesUiState,
    ) {
        totalPages = response.totalPages
        currentPage = response.page
        val newMovies = response.results.map { it.toDomain() }
        val existingMovies = (previousState as? MoviesUiState.Success)?.movies.orEmpty()
        _uiState.value =
            MoviesUiState.Success(
                movies = existingMovies + newMovies,
                isLoadingMore = false,
                page = currentPage,
            )
    }

    private fun handleFailure(
        error: Throwable,
        previousState: MoviesUiState,
    ) {
        if (previousState is MoviesUiState.Success) {
            _uiState.value = previousState.copy(isLoadingMore = false)
        } else {
            _uiState.value =
                MoviesUiState.Error(
                    message = error.message ?: "Unknown error occurred",
                )
        }
    }

    fun retry() {
        _uiState.value = MoviesUiState.Loading
        currentPage = 0
        totalPages = Int.MAX_VALUE
        loadNextPage()
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val loggingInterceptor =
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BASIC
                        }
                    val okHttpClient =
                        OkHttpClient.Builder()
                            .addInterceptor(loggingInterceptor)
                            .build()
                    val retrofit =
                        Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                    val apiService = retrofit.create(MovieApiService::class.java)
                    val repository = MovieRepository(apiService)
                    return MoviesViewModel(repository) as T
                }
            }

        private const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}
