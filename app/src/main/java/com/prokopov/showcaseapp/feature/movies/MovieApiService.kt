package com.prokopov.showcaseapp.feature.movies

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
    ): TopRatedMoviesResponse
}
