package com.joseangelmaneiro.movies.platform.di

import com.joseangelmaneiro.movies.domain.interactor.GetMovies
import com.joseangelmaneiro.movies.platform.features.list.MovieModelFactory
import com.joseangelmaneiro.movies.platform.features.list.MovieListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listModule = module {
  factory { GetMovies(repository = get()) }
  factory { MovieModelFactory() }
  viewModel {
    MovieListViewModel(
      executor = get(),
      getMovies = get(),
      movieModelFactory = get()
    )
  }
}