package com.prokopov.showcaseapp.feature.movies

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() {
        val repository = FakeMovieRepository()
        val viewModel = MoviesViewModel(repository)

        assertTrue(viewModel.uiState.value is MoviesUiState.Loading)
    }

    @Test
    fun `successful load transitions to Success state`() =
        runTest {
            val repository = FakeMovieRepository()
            val viewModel = MoviesViewModel(repository)

            advanceUntilIdle()

            val state = viewModel.uiState.value
            assertTrue(state is MoviesUiState.Success)
            val successState = state as MoviesUiState.Success
            assertEquals(2, successState.movies.size)
            assertEquals(1, successState.page)
            assertFalse(successState.isLoadingMore)
        }

    @Test
    fun `error transitions to Error state`() =
        runTest {
            val repository = FakeMovieRepository(shouldFail = true)
            val viewModel = MoviesViewModel(repository)

            advanceUntilIdle()

            val state = viewModel.uiState.value
            assertTrue(state is MoviesUiState.Error)
            assertEquals("Network error", (state as MoviesUiState.Error).message)
        }

    @Test
    fun `retry resets to Loading then loads successfully`() =
        runTest {
            val repository = FakeMovieRepository(shouldFail = true)
            val viewModel = MoviesViewModel(repository)

            advanceUntilIdle()
            assertTrue(viewModel.uiState.value is MoviesUiState.Error)

            repository.shouldFail = false
            viewModel.retry()

            assertTrue(viewModel.uiState.value is MoviesUiState.Loading)

            advanceUntilIdle()

            val state = viewModel.uiState.value
            assertTrue(state is MoviesUiState.Success)
        }

    @Test
    fun `loadNextPage appends movies to existing list`() =
        runTest {
            val repository = FakeMovieRepository()
            val viewModel = MoviesViewModel(repository)

            advanceUntilIdle()

            val firstState = viewModel.uiState.value as MoviesUiState.Success
            assertEquals(2, firstState.movies.size)

            viewModel.loadNextPage()
            advanceUntilIdle()

            val secondState = viewModel.uiState.value as MoviesUiState.Success
            assertEquals(4, secondState.movies.size)
            assertEquals(2, secondState.page)
        }

    @Test
    fun `movie domain mapping is correct`() {
        val dto =
            MovieDto(
                id = 1,
                title = "Test Movie",
                posterPath = "/abc.jpg",
                releaseDate = "2024-01-15",
                voteAverage = 8.5,
            )
        val movie = dto.toDomain()

        assertEquals(1, movie.id)
        assertEquals("Test Movie", movie.title)
        assertEquals("https://image.tmdb.org/t/p/w500/abc.jpg", movie.posterUrl)
        assertEquals("2024", movie.year)
        assertEquals(8.5, movie.rating, 0.01)
    }

    @Test
    fun `movie domain mapping handles null poster path`() {
        val dto =
            MovieDto(
                id = 1,
                title = "Test",
                posterPath = null,
                releaseDate = "2024-01-15",
                voteAverage = 7.0,
            )
        val movie = dto.toDomain()

        assertEquals(null, movie.posterUrl)
    }

    @Test
    fun `movie domain mapping handles null release date`() {
        val dto =
            MovieDto(
                id = 1,
                title = "Test",
                posterPath = null,
                releaseDate = null,
                voteAverage = 7.0,
            )
        val movie = dto.toDomain()

        assertEquals("", movie.year)
    }
}

private val dummyApiService =
    object : MovieApiService {
        override suspend fun getTopRatedMovies(
            apiKey: String,
            page: Int,
        ): TopRatedMoviesResponse {
            error("Should not be called directly")
        }
    }

private class FakeMovieRepository(
    var shouldFail: Boolean = false,
) : MovieRepository(apiService = dummyApiService) {
    override suspend fun getTopRatedMovies(page: Int): Result<TopRatedMoviesResponse> {
        return if (shouldFail) {
            Result.failure(RuntimeException("Network error"))
        } else {
            Result.success(createResponse(page))
        }
    }

    private fun createResponse(page: Int): TopRatedMoviesResponse {
        val movies =
            listOf(
                MovieDto(
                    id = (page - 1) * 2 + 1,
                    title = "Movie ${(page - 1) * 2 + 1}",
                    posterPath = "/poster1.jpg",
                    releaseDate = "2024-01-01",
                    voteAverage = 8.5,
                ),
                MovieDto(
                    id = (page - 1) * 2 + 2,
                    title = "Movie ${(page - 1) * 2 + 2}",
                    posterPath = "/poster2.jpg",
                    releaseDate = "2023-06-15",
                    voteAverage = 7.2,
                ),
            )
        return TopRatedMoviesResponse(
            page = page,
            totalPages = 5,
            totalResults = 100,
            results = movies,
        )
    }
}
