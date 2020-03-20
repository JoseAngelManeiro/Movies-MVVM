package com.joseangelmaneiro.movies.presentation.presenters

import com.joseangelmaneiro.movies.domain.interactor.GetMovie
import com.joseangelmaneiro.movies.platform.executor.InteractorExecutor
import com.joseangelmaneiro.movies.presentation.DetailMovieView
import com.joseangelmaneiro.movies.presentation.formatters.Formatter
import java.lang.ref.WeakReference

class DetailMoviePresenter(
  private val executor: InteractorExecutor,
  private val getMovie: GetMovie,
  private val formatter: Formatter
) {

  private lateinit var view: WeakReference<DetailMovieView>

  fun setView(detailMovieView: DetailMovieView) {
    view = WeakReference(detailMovieView)
  }

  fun viewReady(movieId: Int) {
    executor(
      interactor = getMovie,
      request = GetMovie.Request(movieId),
      onError = {},
      onSuccess = { movie ->
        view.get()?.let {
          it.displayImage(formatter.getCompleteUrlImage(movie.backdropPath))
          it.displayTitle(movie.title)
          it.displayVoteAverage(movie.voteAverage)
          it.displayReleaseDate(formatter.formatDate(movie.releaseDate))
          it.displayOverview(movie.overview)
        }
      }
    )
  }

  fun navUpSelected() {
    view.get()?.goToBack()
  }
}