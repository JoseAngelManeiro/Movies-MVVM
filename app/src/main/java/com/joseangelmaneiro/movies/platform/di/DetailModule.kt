package com.joseangelmaneiro.movies.platform.di

import com.joseangelmaneiro.movies.domain.interactor.GetMovie
import com.joseangelmaneiro.movies.presentation.MovieDetailModelFactory
import com.joseangelmaneiro.movies.presentation.presenters.DetailMoviePresenter
import org.koin.dsl.module

val detailModule = module {
  factory { GetMovie(repository = get()) }
  factory { MovieDetailModelFactory() }
  factory {
    DetailMoviePresenter(executor = get(), getMovie = get(), movieDetailModelFactory = get())
  }
}