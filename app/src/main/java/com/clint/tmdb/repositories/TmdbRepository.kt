package com.clint.tmdb.repositories

import androidx.lifecycle.LiveData
import com.clint.tmdb.data.local.MovieList
import com.clint.tmdb.data.remote.responses.movieListResponse.MovieListResponse
import com.clint.tmdb.others.Resource

interface TmdbRepository {

    suspend fun insertMovie(movieList: MovieList)

    suspend fun getMovieList(
        apiKey: String,
        page: String
    ): Resource<MovieListResponse>

    fun observeTopRatedMovies() : List<MovieList>

}
