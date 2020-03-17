package com.joseangelmaneiro.movies.platform.di.list

import com.joseangelmaneiro.movies.domain.interactor.UseCaseFactory
import com.joseangelmaneiro.movies.platform.views.MovieListActivity
import com.joseangelmaneiro.movies.presentation.formatters.Formatter
import com.joseangelmaneiro.movies.presentation.presenters.MovieListPresenter
import dagger.Module
import dagger.Provides


@Module
class ListActivityModule {

    @Provides
    internal fun providePresenter(activity: MovieListActivity, useCaseFactory: UseCaseFactory,
                                  formatter: Formatter): MovieListPresenter {
        val presenter = MovieListPresenter(useCaseFactory, formatter)
        presenter.setView(activity)
        return presenter
    }

}