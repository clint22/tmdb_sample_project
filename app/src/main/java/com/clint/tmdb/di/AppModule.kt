package com.clint.tmdb.di

import android.content.Context
import androidx.room.Room
import com.clint.tmdb.data.local.TmdbDao
import com.clint.tmdb.data.local.TmdbDatabase
import com.clint.tmdb.data.remote.TmdbApi
import com.clint.tmdb.others.BASE_URL
import com.clint.tmdb.others.DATABASE_NAME
import com.clint.tmdb.repositories.DefaultTmdbRepository
import com.clint.tmdb.repositories.TmdbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTmdbDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, TmdbDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideDefaultTmdbRepository(
        dao: TmdbDao,
        api: TmdbApi
    ) = DefaultTmdbRepository(dao, api) as TmdbRepository

    @Singleton
    @Provides
    fun provideTmdbDao(
        database: TmdbDatabase
    ) = database.tmdbDao()

    @Singleton
    @Provides
    fun provideTmdbApi(): TmdbApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder =
                original.newBuilder()
                    .method(original.method, original.body)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        httpClient.addInterceptor(interceptor)
        val client = httpClient.build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(TmdbApi::class.java)
    }

}