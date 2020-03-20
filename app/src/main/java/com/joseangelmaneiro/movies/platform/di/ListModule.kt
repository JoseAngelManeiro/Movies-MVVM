package com.joseangelmaneiro.movies.platform.di

import com.joseangelmaneiro.movies.domain.interactor.GetMovies
import com.joseangelmaneiro.movies.presentation.MovieModelFactory
import com.joseangelmaneiro.movies.presentation.presenters.MovieListPresenter
import org.koin.dsl.module

val listModule = module {
  factory { GetMovies(repository = get()) }
  factory { MovieModelFactory() }
  factory {
    MovieListPresenter(executor = get(), getMovies = get(), movieModelFactory = get())
  }
}