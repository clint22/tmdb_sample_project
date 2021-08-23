package com.clint.tmdb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TmdbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieList: MovieList)

    @Query("UPDATE movieList SET status = :status,backdropPath = :backdropPath,genres = :genres,updatedMovieDetails =  :updatedMovieDetails, runTime = :runTime where id = :id")
    suspend fun updateMovie(
        status: String?,
        backdropPath: String?,
        updatedMovieDetails: Boolean,
        genres: String?,
        runTime: Int?,
        id: Int
    )

    @Query("SELECT * FROM movieList")
    fun observeTopRatedMovies(): List<MovieList>

    @Query("SELECT * FROM movieList order by rating ASC")
    fun getTopRatedMoviesByRatingInAscendingOrder(): List<MovieList>

    @Query("SELECT * FROM movieList order by rating DESC")
    fun getTopRatedMoviesByRatingInDescendingOrder(): List<MovieList>

    @Query("SELECT * FROM movieList order by releaseDate ASC")
    fun getTopRatedMoviesByReleaseDateInAscendingOrder(): List<MovieList>

    @Query("SELECT * FROM movieList order by releaseDate DESC")
    fun getTopRatedMoviesByReleaseDateInDescendingOrder(): List<MovieList>

    @Query("SELECT * FROM movieList where id = :movieId")
    fun getTopRatedMoviesByMovieId(movieId: Int): MovieList

}
