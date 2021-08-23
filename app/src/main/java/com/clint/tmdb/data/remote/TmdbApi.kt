package com.clint.tmdb.data.remote

import com.clint.tmdb.data.remote.responses.movieListResponse.MovieListResponse
import com.clint.tmdb.data.remote.responses.movieResponses.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Interface which holds the details of the API.
interface TmdbApi {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovieList(
        @Query("api_key") apiKey: String,
        @Query("page") page: String?
    ): Response<MovieListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>

}