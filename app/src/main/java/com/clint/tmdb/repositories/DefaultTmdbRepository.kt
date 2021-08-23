package com.clint.tmdb.repositories

import com.clint.tmdb.data.local.MovieList
import com.clint.tmdb.data.local.TmdbDao
import com.clint.tmdb.data.remote.TmdbApi
import com.clint.tmdb.data.remote.responses.movieListResponse.MovieListResponse
import com.clint.tmdb.data.remote.responses.movieResponses.MovieResponse
import com.clint.tmdb.others.INTERNET_ERROR_DESCRIPTION
import com.clint.tmdb.others.Resource
import com.clint.tmdb.others.UNKNOWN_ERROR_OCCURRED_DESCRIPTION
import timber.log.Timber
import javax.inject.Inject

class DefaultTmdbRepository @Inject constructor(
    private val tmdbDao: TmdbDao,
    private val tmdbApi: TmdbApi
) : TmdbRepository {

    override fun getTopRatedMoviesByMovieId(movieId: Int): MovieList {
        return tmdbDao.getTopRatedMoviesByMovieId(movieId = movieId)
    }

    override fun getTopRatedMoviesByReleaseDateInDescendingOrder(): List<MovieList> {
        return tmdbDao.getTopRatedMoviesByReleaseDateInDescendingOrder()
    }

    override fun getTopRatedMoviesByReleaseDateInAscendingOrder(): List<MovieList> {
        return tmdbDao.getTopRatedMoviesByReleaseDateInAscendingOrder()
    }

    override fun getTopRatedMoviesByRatingInDescendingOrder(): List<MovieList> {
        return tmdbDao.getTopRatedMoviesByRatingInDescendingOrder()
    }

    override fun getTopRatedMoviesByRatingInAscendingOrder(): List<MovieList> {
        return tmdbDao.getTopRatedMoviesByRatingInAscendingOrder()
    }

    override suspend fun updateMovie(
        status: String?,
        backdropPath: String?,
        updatedMovieDetails: Boolean,
        genres: String?,
        movieId: Int,
        runTime: Int?
    ) {
        tmdbDao.updateMovie(
            status = status,
            backdropPath = backdropPath,
            updatedMovieDetails = updatedMovieDetails,
            genres = genres,
            id = movieId,
            runTime = runTime
        )
    }


    override suspend fun getMovieDetails(apiKey: String, movieId: Int): Resource<MovieResponse> {
        return try {
            val response = tmdbApi.getMovieDetails(apiKey = apiKey, movieId = movieId)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(UNKNOWN_ERROR_OCCURRED_DESCRIPTION, null)
            } else {
                Resource.error(UNKNOWN_ERROR_OCCURRED_DESCRIPTION, null)
            }
        } catch (e: Exception) {
            Timber.e("getMovieDetailsException %s", e.localizedMessage)
            Resource.error(INTERNET_ERROR_DESCRIPTION, null)
        }
    }

    override suspend fun insertMovie(movieList: MovieList) {
        tmdbDao.insertMovie(movieList)
    }

    override suspend fun getTopRatedMovieList(
        apiKey: String,
        page: String
    ): Resource<MovieListResponse> {
        return try {
            val response = tmdbApi.getTopRatedMovieList(apiKey = apiKey, page = "1")
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