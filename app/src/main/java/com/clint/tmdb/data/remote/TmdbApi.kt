package com.clint.tmdb.data.remote

import com.clint.tmdb.data.remote.responses.movieListResponse.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {

    @GET("movie/top_rated")
    suspend fun getMovieList(
        @Query("api_key") apiKey: String,
        @Query("page") page: String?
    ): Response<MovieListResponse>
}