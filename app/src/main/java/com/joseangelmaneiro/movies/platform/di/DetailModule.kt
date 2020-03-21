package com.joseangelmaneiro.movies.platform.di

import com.joseangelmaneiro.movies.domain.interactor.GetMovie
import com.joseangelmaneiro.movies.platform.features.detail.MovieDetailModelFactory
import com.joseangelmaneiro.movies.platform.features.detail.DetailMovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
  factory { GetMovie(repository = get()) }
  factory { MovieDetailModelFactory() }
  viewModel {
    DetailMovieViewModel(
      executor = get(),
      getMovie = get(),
      movieDetailModelFactory = get()
    )
  }
}