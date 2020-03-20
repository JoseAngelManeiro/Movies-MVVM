package com.joseangelmaneiro.movies.presentation.presenters

import androidx.lifecycle.ViewModel
import com.joseangelmaneiro.movies.domain.model.Movie
import com.joseangelmaneiro.movies.domain.interactor.GetMovies
import com.joseangelmaneiro.movies.platform.executor.InteractorExecutor
import com.joseangelmaneiro.movies.presentation.MovieListView
import java.lang.ref.WeakReference

class MovieListPresenter(
  private val executor: InteractorExecutor,
  private val getMovies: GetMovies
) : ViewModel() {

  private lateinit var view: WeakReference<MovieListView>

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
        view.get()?.let {
          it.cancelRefreshDialog()
          it.showMovies(movies)
        }
      }
    )
  }

  fun onItemClick(movie: Movie) {
    view.get()?.navigateToDetailScreen(movie.id)
  }
}