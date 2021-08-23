package com.clint.tmdb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TmdbDao {

    //    Query to insert the movies
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieList: MovieList)

    //    Query to update the movie details using the movie ID
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

    //    Query to get the top rated movies in the ascending order of rating
    @Query("SELECT * FROM movieList order by rating ASC")
    fun getTopRatedMoviesByRatingInAscendingOrder(): List<MovieList>

    //  Query to get the top rated movies in the descending order of rating
    @Query("SELECT * FROM movieList order by rating DESC")
    fun getTopRatedMoviesByRatingInDescendingOrder(): List<MovieList>

    //  Query to get the top rated movies in the ascending order of release date
    @Query("SELECT * FROM movieList order by releaseDate ASC")
    fun getTopRatedMoviesByReleaseDateInAscendingOrder(): List<MovieList>

    //  Query to get the top rated movies in the descending order of release date
    @Query("SELECT * FROM movieList order by releaseDate DESC")
    fun getTopRatedMoviesByReleaseDateInDescendingOrder(): List<MovieList>

    // Query to get the top rated movies using the movie Id
    @Query("SELECT * FROM movieList where id = :movieId")
    fun getTopRatedMoviesByMovieId(movieId: Int): MovieList

}
