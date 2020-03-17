package com.joseangelmaneiro.movies.platform.di.detail

import com.joseangelmaneiro.movies.domain.interactor.UseCaseFactory
import com.joseangelmaneiro.movies.platform.views.DetailMovieActivity
import com.joseangelmaneiro.movies.presentation.formatters.Formatter
import com.joseangelmaneiro.movies.presentation.presenters.DetailMoviePresenter
import dagger.Module
import dagger.Provides


@Module
class DetailActivityModule {

    @Provides
    internal fun providePresenter(activity: DetailMovieActivity, useCaseFactory: UseCaseFactory,
                                  formatter: Formatter): DetailMoviePresenter {
        val movieId = activity.intent.getIntExtra(DetailMovieActivity.EXTRA_MOVIE_ID, -1)
        val presenter = DetailMoviePresenter(useCaseFactory, formatter, movieId)
        presenter.setView(activity)
        return presenter
    }

}
