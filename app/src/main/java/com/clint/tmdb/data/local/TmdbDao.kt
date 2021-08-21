package com.clint.tmdb.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TmdbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieList: MovieList)

    @Query("SELECT * FROM movieList")
    fun observeTopRatedMovies() : List<MovieList>

}
