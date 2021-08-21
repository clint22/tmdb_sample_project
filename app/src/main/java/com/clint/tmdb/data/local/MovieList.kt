package com.clint.tmdb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieList")
data class MovieList(
    @PrimaryKey
    val id: Int,
    val title: String?,
    val director: String?,
    val rating: Double?,
    val releaseDate: String?,
    val posterPath: String?,
    val overView: String?,
    val status: String?,
    val tagline: String?
)