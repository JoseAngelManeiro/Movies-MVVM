package com.joseangelmaneiro.movies.presentation.presenters

import com.joseangelmaneiro.movies.domain.model.Movie
import com.joseangelmaneiro.movies.domain.interactor.GetMovies
import com.joseangelmaneiro.movies.platform.executor.InteractorExecutor
import com.joseangelmaneiro.movies.presentation.MovieCellView
import com.joseangelmaneiro.movies.presentation.MovieListView
import com.joseangelmaneiro.movies.presentation.formatters.Formatter
import java.lang.ref.WeakReference

class MovieListPresenter(
  private val executor: InteractorExecutor,
  private val getMovies: GetMovies,
  private val formatter: Formatter
) {

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
    executor(
      interactor = getMovies,
      request = GetMovies.Request(refresh),
      onError = { exception ->
        view.get()?.let {
          it.cancelRefreshDialog()
          it.showErrorMessage(exception.message!!)
        }
      },
      onSuccess = { movies ->
        saveMovies(movies)
        view.get()?.let {
          it.cancelRefreshDialog()
          it.refreshList()
        }
      }
    )
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