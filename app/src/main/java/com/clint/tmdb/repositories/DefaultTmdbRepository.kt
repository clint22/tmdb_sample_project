package com.clint.tmdb.repositories

import androidx.lifecycle.LiveData
import com.clint.tmdb.data.local.MovieList
import com.clint.tmdb.data.local.TmdbDao
import com.clint.tmdb.data.remote.TmdbApi
import com.clint.tmdb.data.remote.responses.movieListResponse.MovieListResponse
import com.clint.tmdb.others.INTERNET_ERROR_DESCRIPTION
import com.clint.tmdb.others.Resource
import com.clint.tmdb.others.UNKNOWN_ERROR_OCCURRED_DESCRIPTION
import javax.inject.Inject

class DefaultTmdbRepository @Inject constructor(
    private val tmdbDao: TmdbDao,
    private val tmdbApi: TmdbApi
) : TmdbRepository {

    override suspend fun insertMovie(movieList: MovieList) {
        tmdbDao.insertMovie(movieList)
    }

    override suspend fun getMovieList(apiKey: String, page: String): Resource<MovieListResponse> {
        return try {
            val response = tmdbApi.getMovieList(apiKey = apiKey, page = "1")
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(UNKNOWN_ERROR_OCCURRED_DESCRIPTION, null)
            } else {
                Resource.error(UNKNOWN_ERROR_OCCURRED_DESCRIPTION, null)
            }

        } catch (e: Exception) {
            Resource.error(INTERNET_ERROR_DESCRIPTION, null)
        }
    }

    override fun observeTopRatedMovies(): List<MovieList> {
        return tmdbDao.observeTopRatedMovies()
    }

}