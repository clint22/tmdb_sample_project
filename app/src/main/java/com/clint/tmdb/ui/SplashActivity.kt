package com.clint.tmdb.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.clint.tmdb.BuildConfig
import com.clint.tmdb.databinding.ActivitySplashBinding
import com.clint.tmdb.others.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

@DelicateCoroutinesApi
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val tmdbViewModel: TmdbViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        tmdbViewModel
        getTopRatedMoviesList()
        observeViewModel()
    }

    private fun observeViewModel() {


        tmdbViewModel.movieList.observe(this, { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    Timber.e("movie_list_result %s", response.data)
                    for (item in response.data?.results!!) {
                        tmdbViewModel.insertMovie(item)
                    }
                    gotoHomeActivity()
                }
                Status.ERROR -> {
                    Timber.e(response.message)
                    showErrorView(response.message)
                }
                Status.LOADING -> {
                    Timber.e("Loading")
                }
            }
        })

    }

    private fun showErrorView(message: String?) {
        binding.parentConstraintLayout.visibility = View.GONE
        binding.linearLayoutErrorView.visibility = View.VISIBLE
        binding.textViewErrorDescription.text = message
    }

    private fun getTopRatedMoviesList() {
        GlobalScope.launch {
            val topRatedMovies = tmdbViewModel.getTopRatedMovies()
            Timber.e("top_rated_movie_list %s", topRatedMovies)
            if (topRatedMovies.isNullOrEmpty()) {
                tmdbViewModel.getTopRatedMovieList(apiKey = BuildConfig.API_KEY, page = "1")
            } else {
                Handler(mainLooper).postDelayed({
                    gotoHomeActivity()
                }, 2000)

            }
        }

    }

    private fun gotoHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}