package com.clint.tmdb.repositories

import com.clint.tmdb.data.local.MovieList
import com.clint.tmdb.data.remote.responses.movieListResponse.MovieListResponse
import com.clint.tmdb.data.remote.responses.movieResponses.MovieResponse
import com.clint.tmdb.others.Resource

// This interface will hold the complete details of the functionalities that this app needs. Whichever class extends this interface, will
// have to define the function details. This method is useful when it comes to testing.
interface TmdbRepository {

    suspend fun insertMovie(movieList: MovieList)

    suspend fun updateMovie(
        status: String?,
        backdropPath: String?,
        updatedMovieDetails: Boolean,
        genres: String?,
        movieId: Int,
        runTime: Int?
    )

    suspend fun getTopRatedMovieList(
        apiKey: String,
        page: String
    ): Resource<MovieListResponse>

    fun observeTopRatedMovies(): List<MovieList>

    fun getTopRatedMoviesByRatingInAscendingOrder(): List<MovieList>

    fun getTopRatedMoviesByRatingInDescendingOrder(): List<MovieList>

    fun getTopRatedMoviesByReleaseDateInAscendingOrder(): List<MovieList>

    fun getTopRatedMoviesByReleaseDateInDescendingOrder(): List<MovieList>

    fun getTopRatedMoviesByMovieId(movieId: Int): MovieList

    suspend fun getMovieDetails(
        apiKey: String,
        movieId: Int
    ): Resource<MovieResponse>

}
