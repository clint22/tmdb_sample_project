package com.clint.tmdb.data.remote.responses.movieListResponse

data class MovieListResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)