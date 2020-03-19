package com.joseangelmaneiro.movies.platform.di.detail

import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.joseangelmaneiro.movies.domain.interactor.GetMovie
import com.joseangelmaneiro.movies.platform.executor.InteractorExecutor
import com.joseangelmaneiro.movies.platform.views.DetailMovieActivity
import com.joseangelmaneiro.movies.presentation.formatters.Formatter
import com.joseangelmaneiro.movies.presentation.presenters.DetailMoviePresenter
import dagger.Module
import dagger.Provides

@Module
class DetailActivityModule {

  @Provides
  internal fun provideGetMovie(moviesRepository: MoviesRepository): GetMovie {
    return GetMovie(moviesRepository)
  }

  @Provides
  internal fun providePresenter(
    activity: DetailMovieActivity,
    executor: InteractorExecutor,
    getMovie: GetMovie,
    formatter: Formatter
  ): DetailMoviePresenter {
    val movieId = activity.intent.getIntExtra(DetailMovieActivity.EXTRA_MOVIE_ID, -1)
    val presenter = DetailMoviePresenter(executor, getMovie, formatter, movieId)
    presenter.setView(activity)
    return presenter
  }
}
