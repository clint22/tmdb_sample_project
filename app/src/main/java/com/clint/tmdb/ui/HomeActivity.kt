package com.clint.tmdb.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.clint.tmdb.R
import com.clint.tmdb.data.local.MovieList
import com.clint.tmdb.databinding.ActivityHomeBinding
import com.clint.tmdb.others.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber

@DelicateCoroutinesApi
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private lateinit var binding: ActivityHomeBinding
    private val tmdbViewModel: TmdbViewModel by viewModels()

    //    A dialog that shows the different types of sorting options available.
    private var customFilterDialog: CustomFilterDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        tmdbViewModel

//        Fetching the top rated movies list using the Room DB.
        getTopRatedMovies()

        binding.imageViewSorting.setOnClickListener {
            customFilterDialog = CustomFilterDialog(this) { BUTTON_CLICKED ->
                customFilterDialog?.dismiss()
                getTopRatedMoviesBySorting(BUTTON_CLICKED)
            }
            customFilterDialog!!.show()
        }

        binding.textViewSortedBy.visibility = View.GONE
    }

    //    A function that checks which sorting option is clicked and update the movie list according to that.
//    A new Movie list is fetched according to the sorting param.
    private fun getTopRatedMoviesBySorting(sortedBy: Int) {
        var sortedByDescription: String? = null
        GlobalScope.launch {
            var sortedTopRatedMovies: List<MovieList>? = null
            when (sortedBy) {
                CUSTOM_DIALOG_RATING_ASC_ORDER_CLICKED -> {
                    sortedByDescription = getString(R.string.sorted_by_rating_ascending)
                    sortedTopRatedMovies = tmdbViewModel.getTopRatedMoviesByRatingInAscendingOrder()
                }
                CUSTOM_DIALOG_RATING_DESC_ORDER_CLICKED -> {
                    sortedByDescription = getString(R.string.sorted_by_rating_descending)
                    sortedTopRatedMovies =
                        tmdbViewModel.getTopRatedMoviesByRatingInDescendingOrder()
                }
                CUSTOM_DIALOG_RELEASE_DATE_ASC_ORDER_CLICKED -> {
                    sortedByDescription = getString(R.string.sorted_by_release_date_ascending)
                    sortedTopRatedMovies =
                        tmdbViewModel.getTopRatedMoviesByReleaseDateInAscendingOrder()
                }
                CUSTOM_DIALOG_RELEASE_DATE_DESC_ORDER_CLICKED -> {
                    sortedByDescription = getString(R.string.sorted_by_release_date_descending)
                    sortedTopRatedMovies =
                        tmdbViewModel.getTopRatedMoviesByReleaseDateInDescendingOrder()
                }
            }
            withContext(Dispatchers.Main) {
                binding.textViewSortedBy.visibility = View.VISIBLE
                binding.textViewSortedBy.text = sortedByDescription
                setAdapter(sortedTopRatedMovies)
            }
        }
    }


    //  A recycler view with a linear layout manager, and a lambda function that returns the movie ID and
//    redirects to the movie details screen with the movie ID.
    private fun setAdapter(movieList: List<MovieList>?) {

        val adapter = MovieListAdapter(movieList, { movieId ->

            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID_NAME, movieId)
            startActivity(intent)

        }, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    //    Will fetch the top rated movies list and then pass it to the adapter.
    private fun getTopRatedMovies() {
        GlobalScope.launch {
            val topRatedMovies = tmdbViewModel.getTopRatedMovies()
            Timber.e("topRatedMoviesHome %s", topRatedMovies)
            setAdapter(topRatedMovies)
        }
    }

    //    Checking if the back button is clicked twice, if, then, we will exit the app.
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}