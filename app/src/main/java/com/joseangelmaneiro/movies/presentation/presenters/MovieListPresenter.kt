package com.joseangelmaneiro.movies.presentation.presenters

import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.Observer
import com.joseangelmaneiro.movies.domain.interactor.GetMovies
import com.joseangelmaneiro.movies.domain.interactor.UseCaseFactory
import com.joseangelmaneiro.movies.presentation.MovieCellView
import com.joseangelmaneiro.movies.presentation.MovieListView
import com.joseangelmaneiro.movies.presentation.formatters.Formatter
import java.lang.ref.WeakReference


class MovieListPresenter(
  private val useCaseFactory: UseCaseFactory,
  private val formatter: Formatter
) : BasePresenter() {

  private lateinit var view: WeakReference<MovieListView>

  private var movieList = emptyList<Movie>()

  private var selectedMovieId: Int = 0


  fun setView(movieListView: MovieListView) {
    view = WeakReference(movieListView)
  }

  fun viewReady() {
    invokeGetMovies(false)
  }

  fun refresh() {
    invokeGetMovies(true)
  }

  private fun invokeGetMovies(refresh: Boolean) {
    val useCase = useCaseFactory.getMovies()
    addDisposable(useCase.execute(MoviesObserver(), GetMovies.Params(refresh)))
  }

  private inner class MoviesObserver : Observer<List<Movie>>() {
    override fun onSuccess(movies: List<Movie>) {
      saveMovies(movies)
      view.get()?.let {
        it.cancelRefreshDialog()
        it.refreshList()
      }
    }

    override fun onError(exception: Throwable) {
      view.get()?.let {
        it.cancelRefreshDialog()
        it.showErrorMessage(exception.message!!)
      }
    }
  }

  fun getItemsCount(): Int {
    return if (moviesListIsEmpty()) 0 else movieList.size
  }

  fun configureCell(movieCellView: MovieCellView, position: Int) {
    val movie = getMovie(position)
    movieCellView.displayImage(formatter.getCompleteUrlImage(movie.posterPath))
  }

  fun onItemClick(position: Int) {
    val movie = getMovie(position)
    saveSelectedMovieId(movie.id)
    view.get()?.navigateToDetailScreen(getSelectedMovieId())
  }

  fun saveMovies(movieList: List<Movie>) {
    this.movieList = movieList
  }

  private fun getMovie(position: Int): Movie {
    return movieList[position]
  }

  private fun saveSelectedMovieId(selectedMovieId: Int) {
    this.selectedMovieId = selectedMovieId
  }

  fun moviesListIsEmpty(): Boolean {
    return movieList.isEmpty()
  }

  fun getSelectedMovieId(): Int {
    return selectedMovieId
  }

}