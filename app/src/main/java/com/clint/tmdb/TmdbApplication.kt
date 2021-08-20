package com.clint.tmdb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TmdbApplication : Application() {
    companion object {
        @get:Synchronized
        var sInstance: TmdbApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        sInstance = this
    }
}