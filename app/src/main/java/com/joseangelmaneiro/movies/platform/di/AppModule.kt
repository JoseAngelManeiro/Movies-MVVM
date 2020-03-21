package com.joseangelmaneiro.movies.platform.di

import android.database.sqlite.SQLiteOpenHelper
import com.joseangelmaneiro.movies.data.MoviesRepositoryImpl
import com.joseangelmaneiro.movies.data.mapper.MovieMapper
import com.joseangelmaneiro.movies.data.source.local.MoviesDatabaseHelper
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSource
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSourceImpl
import com.joseangelmaneiro.movies.data.source.remote.MovieService
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSource
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSourceImpl
import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.joseangelmaneiro.movies.platform.Formatter
import com.joseangelmaneiro.movies.platform.executor.AsyncInteractorExecutor
import com.joseangelmaneiro.movies.platform.executor.BackgroundRunner
import com.joseangelmaneiro.movies.platform.executor.InteractorExecutor
import com.joseangelmaneiro.movies.platform.executor.MainRunner
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

  single<SQLiteOpenHelper> { MoviesDatabaseHelper(androidContext()) }

  single<MovieService> {
    Retrofit.Builder()
      .baseUrl("https://api.themoviedb.org/3/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create<MovieService>(MovieService::class.java)
  }

  single<MoviesLocalDataSource> { MoviesLocalDataSourceImpl(sqLiteOpenHelper = get()) }

  single<MoviesRemoteDataSource> { MoviesRemoteDataSourceImpl(movieService = get()) }

  single<MoviesRepository> {
    MoviesRepositoryImpl(
      localDataSource = get(),
      remoteDataSource = get(),
      movieMapper = MovieMapper()
    )
  }

  single<InteractorExecutor> {
    AsyncInteractorExecutor(
      runOnBgThread = BackgroundRunner(),
      runOnMainThread = MainRunner()
    )
  }

  single { Formatter() }
}