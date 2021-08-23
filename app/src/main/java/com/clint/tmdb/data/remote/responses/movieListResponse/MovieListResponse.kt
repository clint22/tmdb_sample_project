package com.clint.tmdb.data.remote.responses.movieListResponse

// The data class which holds the details of the movie List responses.
data class MovieListResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)