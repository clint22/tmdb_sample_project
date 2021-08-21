package com.clint.tmdb.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.clint.tmdb.data.local.MovieList
import com.clint.tmdb.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

@DelicateCoroutinesApi
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val tmdbViewModel: TmdbViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        tmdbViewModel
        getTopRatedMovies()
    }


    private fun setAdapter(movieList: List<MovieList>?) {

        val adapter = MovieListAdapter(movieList, this)
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