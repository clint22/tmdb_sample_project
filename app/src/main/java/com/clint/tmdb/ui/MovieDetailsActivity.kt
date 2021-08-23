package com.clint.tmdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.clint.tmdb.BuildConfig
import com.clint.tmdb.R
import com.clint.tmdb.data.local.MovieList
import com.clint.tmdb.data.remote.responses.movieResponses.MovieResponse
import com.clint.tmdb.databinding.ActivityMovieDetailsBinding
import com.clint.tmdb.others.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber

@DelicateCoroutinesApi
@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val tmdbViewModel: TmdbViewModel by viewModels()
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.lottieAnimationView.visibility = View.VISIBLE
        binding.parentConstraintLayout.visibility = View.GONE

        tmdbViewModel
        getPassedData()
        observeViewModel()

        binding.textViewBackButton.setOnClickListener { onBackPressed() }
    }

    private fun observeViewModel() {
        tmdbViewModel.movieDetails.observe(this, { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    Timber.e("movie_details_result %s", response.data)
                    val data = response.data
                    updateMovieDetails(data)
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

    private fun updateMovieDetails(data: MovieResponse?) {
        var genres: String? = null
        if (data != null) {
            val commaSeparatedString =
                data.genres.joinToString { it.name }
            genres = commaSeparatedString
            Timber.e("commaSeparatedString %s", commaSeparatedString)
        }
        tmdbViewModel.updateMovie(
            status = data?.status,
            movieId = movieId,
            genres = genres,
            backdropPath = data?.backdrop_path,
            updatedMovieDetails = true,
            runTime = data?.runtime
        )
        checkIfMovieDetailsAlreadyUpdated()
    }

    private fun getPassedData() {
        movieId = intent.getIntExtra(MOVIE_ID_NAME, 0)
        Timber.e("movieId %s", movieId)
        if (movieId != 0) {
            checkIfMovieDetailsAlreadyUpdated()
        }
    }

    private fun checkIfMovieDetailsAlreadyUpdated() {
        GlobalScope.launch {
            val topRatedMoviesByMovieId = tmdbViewModel.getTopRatedMoviesByMovieId(movieId)
            if (topRatedMoviesByMovieId.updatedMovieDetails) {
                withContext(Dispatchers.Main) {
                    setupUI(topRatedMoviesByMovieId)

                }
            } else {
                tmdbViewModel.getMovieDetails(apiKey = BuildConfig.API_KEY, movieId = movieId)
            }
        }
    }

    private fun setupUI(movie: MovieList) {

        binding.lottieAnimationView.visibility = View.GONE
        binding.parentConstraintLayout.visibility = View.VISIBLE

        binding.textViewToolbarTitle.text = movie.title
        binding.textViewGenres.text = movie.genres
        binding.textViewMovieTitleWithDate.text =
            getString(
                R.string.movie_name_with_release_date,
                movie.title,
                movie.releaseDate?.convertStringToDate()?.formatToViewDateOnly()
            )
        binding.textViewMovieReleaseStatus.text = movie.status
        binding.textViewOverView.text = movie.overView
        binding.textViewRunTime.text =
            movie.runTime?.convertMinutesToHours(this) ?: "Duration not available"
        val posterImageCompletePath = POSTER_PATH_BASE_URL + movie.backdropPath

        Glide
            .with(this)
            .load(posterImageCompletePath)
            .centerCrop()
            .placeholder(R.drawable.no_image_view_holder)
            .into(binding.imageViewBackdrop)
    }
}