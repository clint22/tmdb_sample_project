package com.clint.tmdb.ui

import androidx.lifecycle.*
import com.clint.tmdb.data.remote.responses.movieListResponse.MovieListResponse
import com.clint.tmdb.others.Resource
import com.clint.tmdb.repositories.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TmdbViewModel @Inject constructor(private val tmdbRepository: TmdbRepository) : ViewModel(), LifecycleObserver {

    private val _movieList = MutableLiveData<Resource<MovieListResponse>>()
    val movieList: LiveData<Resource<MovieListResponse>> = _movieList

    fun getMovieList(apiKey: String, page: String) {
        _movieList.postValue(Resource.loading(null))
        viewModelScope.launch {
            val response = tmdbRepository.getMovieList(apiKey = apiKey, page = page)
            _movieList.postValue(response)
        }
    }
}

