package com.joseangelmaneiro.movies.platform

import android.app.Application
import com.joseangelmaneiro.movies.platform.di.appModule
import com.joseangelmaneiro.movies.platform.di.detailModule
import com.joseangelmaneiro.movies.platform.di.listModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoviesApp : Application() {

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidLogger()
      androidContext(this@MoviesApp)
      modules(listOf(
        appModule,
        listModule,
        detailModule
      ))
    }
  }
}