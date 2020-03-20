package com.joseangelmaneiro.movies.presentation.presenters

import androidx.lifecycle.ViewModel
import com.joseangelmaneiro.movies.domain.interactor.GetMovies
import com.joseangelmaneiro.movies.platform.executor.InteractorExecutor
import com.joseangelmaneiro.movies.presentation.MovieListView
import com.joseangelmaneiro.movies.presentation.MovieModelFactory
import com.joseangelmaneiro.movies.presentation.model.MovieModel
import java.lang.ref.WeakReference

class MovieListPresenter(
  private val executor: InteractorExecutor,
  private val getMovies: GetMovies,
  private val movieModelFactory: MovieModelFactory
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
          it.showMovies(movieModelFactory.createMovieModels(movies))
        }
      }
    )
  }

  fun onItemClick(movieModel: MovieModel) {
    view.get()?.navigateToDetailScreen(movieModel.id)
  }
}