package com.clint.tmdb.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.clint.tmdb.BuildConfig
import com.clint.tmdb.databinding.ActivitySplashBinding
import com.clint.tmdb.others.Status
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val tmdbViewModel: TmdbViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getTopRatedMoviesList()
        observeViewModel()
    }

    private fun observeViewModel() {
        tmdbViewModel.movieList.observe(this, { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    Timber.e("movie_list_result %s", response.data)
                }
                Status.ERROR -> {
                    Timber.e(response.message)
                }
                Status.LOADING -> {
                    Timber.e("Loading")
                }
            }
        })
    }

    private fun getTopRatedMoviesList() {
        tmdbViewModel.getMovieList(apiKey = BuildConfig.API_KEY, page = "1")
    }
}