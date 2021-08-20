package com.clint.tmdb.repositories

import com.clint.tmdb.data.local.MovieList
import com.clint.tmdb.data.remote.responses.movieListResponse.MovieListResponse
import com.clint.tmdb.others.Resource

interface TmdbRepository {

    suspend fun insertMovie(movieList: MovieList)

    suspend fun getMovieList(
        apiKey: String,
        page: String
    ): Resource<MovieListResponse>

}
