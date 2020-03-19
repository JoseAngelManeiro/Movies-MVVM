package com.joseangelmaneiro.movies.platform.di.list

import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.joseangelmaneiro.movies.domain.interactor.GetMovies
import com.joseangelmaneiro.movies.platform.executor.InteractorExecutor
import com.joseangelmaneiro.movies.platform.views.MovieListActivity
import com.joseangelmaneiro.movies.presentation.formatters.Formatter
import com.joseangelmaneiro.movies.presentation.presenters.MovieListPresenter
import dagger.Module
import dagger.Provides

@Module
class ListActivityModule {

  @Provides
  internal fun provideGetMovies(moviesRepository: MoviesRepository): GetMovies {
    return GetMovies(moviesRepository)
  }

  @Provides
  internal fun providePresenter(
    activity: MovieListActivity,
    executor: InteractorExecutor,
    getMovies: GetMovies,
    formatter: Formatter
  ): MovieListPresenter {
    val presenter = MovieListPresenter(executor, getMovies, formatter)
    presenter.setView(activity)
    return presenter
  }
}