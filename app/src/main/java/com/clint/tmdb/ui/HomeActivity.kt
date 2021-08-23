package com.clint.tmdb.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.clint.tmdb.data.local.MovieList
import com.clint.tmdb.databinding.ActivityHomeBinding
import com.clint.tmdb.others.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber

@DelicateCoroutinesApi
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val tmdbViewModel: TmdbViewModel by viewModels()
    private var customFilterDialog: CustomFilterDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        tmdbViewModel
        getTopRatedMovies()

        binding.imageViewSorting.setOnClickListener {
            customFilterDialog = CustomFilterDialog(this) { BUTTON_CLICKED ->
                customFilterDialog?.dismiss()
                getTopRatedMoviesBySorting(BUTTON_CLICKED)
            }
            customFilterDialog!!.show()
        }
    }

    private fun getTopRatedMoviesBySorting(sortedBy: Int) {
        GlobalScope.launch {
            var sortedTopRatedMovies: List<MovieList>? = null
            when (sortedBy) {
                CUSTOM_DIALOG_RATING_ASC_ORDER_CLICKED -> {
                    sortedTopRatedMovies = tmdbViewModel.getTopRatedMoviesByRatingInAscendingOrder()
                }
                CUSTOM_DIALOG_RATING_DESC_ORDER_CLICKED -> {
                    sortedTopRatedMovies =
                        tmdbViewModel.getTopRatedMoviesByRatingInDescendingOrder()
                }
                CUSTOM_DIALOG_RELEASE_DATE_ASC_ORDER_CLICKED -> {
                    sortedTopRatedMovies =
                        tmdbViewModel.getTopRatedMoviesByReleaseDateInAscendingOrder()
                }
                CUSTOM_DIALOG_RELEASE_DATE_DESC_ORDER_CLICKED -> {
                    sortedTopRatedMovies =
                        tmdbViewModel.getTopRatedMoviesByReleaseDateInDescendingOrder()
                }
            }
            withContext(Dispatchers.Main) {
                setAdapter(sortedTopRatedMovies)
            }
        }
    }


    private fun setAdapter(movieList: List<MovieList>?) {

        val adapter = MovieListAdapter(movieList, { movieId ->

            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID_NAME, movieId)
            startActivity(intent)

        }, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getTopRatedMovies() {
        GlobalScope.launch {
            val topRatedMovies = tmdbViewModel.getTopRatedMovies()
            Timber.e("topRatedMoviesHome %s", topRatedMovies)
            setAdapter(topRatedMovies)
        }
    }
}