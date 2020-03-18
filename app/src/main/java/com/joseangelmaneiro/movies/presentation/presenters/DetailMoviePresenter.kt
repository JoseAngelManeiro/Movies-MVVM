package com.joseangelmaneiro.movies.presentation.presenters

import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.Observer
import com.joseangelmaneiro.movies.domain.interactor.GetMovie
import com.joseangelmaneiro.movies.domain.interactor.UseCaseFactory
import com.joseangelmaneiro.movies.presentation.DetailMovieView
import com.joseangelmaneiro.movies.presentation.formatters.Formatter
import java.lang.ref.WeakReference


class DetailMoviePresenter(
  private val useCaseFactory: UseCaseFactory,
  private val formatter: Formatter,
  private val movieId: Int
) : BasePresenter() {

  private lateinit var view: WeakReference<DetailMovieView>


  fun setView(detailMovieView: DetailMovieView) {
    view = WeakReference(detailMovieView)
  }

  fun viewReady() {
    val useCase = useCaseFactory.getMovie()
    addDisposable(useCase.execute(MovieObserver(), GetMovie.Params(movieId)))
  }

  private inner class MovieObserver : Observer<Movie>() {
    override fun onSuccess(movie: Movie) {
      view.get()?.let {
        it.displayImage(formatter.getCompleteUrlImage(movie.backdropPath))
        it.displayTitle(movie.title)
        it.displayVoteAverage(movie.voteAverage)
        it.displayReleaseDate(formatter.formatDate(movie.releaseDate))
        it.displayOverview(movie.overview)
      }
    }
  }

  fun navUpSelected() {
    view.get()?.goToBack()
  }

}