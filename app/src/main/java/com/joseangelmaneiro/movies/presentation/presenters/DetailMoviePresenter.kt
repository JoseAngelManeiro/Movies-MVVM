package com.joseangelmaneiro.movies.presentation.presenters

import com.joseangelmaneiro.movies.domain.interactor.GetMovie
import com.joseangelmaneiro.movies.platform.executor.InteractorExecutor
import com.joseangelmaneiro.movies.presentation.DetailMovieView
import com.joseangelmaneiro.movies.presentation.MovieDetailModelFactory
import java.lang.ref.WeakReference

class DetailMoviePresenter(
  private val executor: InteractorExecutor,
  private val getMovie: GetMovie,
  private val movieDetailModelFactory: MovieDetailModelFactory
) {

  private lateinit var view: WeakReference<DetailMovieView>

  fun setView(detailMovieView: DetailMovieView) {
    view = WeakReference(detailMovieView)
  }

  fun viewReady(movieId: Int) {
    executor(
      interactor = getMovie,
      request = GetMovie.Request(movieId),
      onError = {
        view.get()?.showErrorMessage(it.message!!)
      },
      onSuccess = {
        view.get()?.displayMovieDetailModel(movieDetailModelFactory.createMovieDetailModel(it))
      }
    )
  }

  fun navUpSelected() {
    view.get()?.goToBack()
  }
}