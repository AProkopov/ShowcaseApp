package com.prokopov.showcaseapp.feature.movies

data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val year: String,
    val rating: Double,
)

fun MovieDto.toDomain(): Movie =
    Movie(
        id = id,
        title = title,
        posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        year = releaseDate?.take(4).orEmpty(),
        rating = voteAverage,
    )
