package com.clint.tmdb.ui

import androidx.lifecycle.*
import com.clint.tmdb.data.local.MovieList
import com.clint.tmdb.data.remote.responses.movieListResponse.MovieListResponse
import com.clint.tmdb.data.remote.responses.movieListResponse.Result
import com.clint.tmdb.others.Resource
import com.clint.tmdb.repositories.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TmdbViewModel @Inject constructor(private val tmdbRepository: TmdbRepository) : ViewModel(),
    LifecycleObserver {

    private val _movieList = MutableLiveData<Resource<MovieListResponse>>()
    val movieList: LiveData<Resource<MovieListResponse>> = _movieList

    private val _topRatedMoviesList = MutableLiveData<List<MovieList>>()
    val topRatedMoviesList: LiveData<List<MovieList>> = _topRatedMoviesList


    fun getMovieList(apiKey: String, page: String) {
        _movieList.postValue(Resource.loading(null))
        viewModelScope.launch {
            val response = tmdbRepository.getMovieList(apiKey = apiKey, page = page)
            _movieList.postValue(response)
        }
    }

    fun insertMovie(movieDetails: Result) {

        val movieList = MovieList(
            id = movieDetails.id,
            title = movieDetails.title,
            director = null,
            rating = movieDetails.vote_average,
            releaseDate = movieDetails.release_date,
            posterPath = movieDetails.poster_path,
            overView = movieDetails.overview,
            status = null,
            tagline = null
        )
        insertMovieIntoDb(movieList)
    }

    private fun insertMovieIntoDb(movieList: MovieList) = viewModelScope.launch {
        tmdbRepository.insertMovie(movieList)
    }

    fun getTopRatedMovies(): List<MovieList> {
        return tmdbRepository.observeTopRatedMovies()

    }
}
