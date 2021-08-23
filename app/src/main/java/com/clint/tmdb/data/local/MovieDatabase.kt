package com.clint.tmdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieList::class],
    version = 3
)
abstract class TmdbDatabase : RoomDatabase() {
    abstract fun tmdbDao(): TmdbDao
}
