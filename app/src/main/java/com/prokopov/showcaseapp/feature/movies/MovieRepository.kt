package com.prokopov.showcaseapp.feature.movies

import com.prokopov.showcaseapp.BuildConfig

open class MovieRepository(
    private val apiService: MovieApiService,
) {
    open suspend fun getTopRatedMovies(page: Int): Result<TopRatedMoviesResponse> =
        runCatching {
            apiService.getTopRatedMovies(
                apiKey = BuildConfig.TMDB_API_KEY,
                page = page,
            )
        }
}
