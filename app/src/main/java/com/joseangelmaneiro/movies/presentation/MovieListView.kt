package com.joseangelmaneiro.movies.presentation

import com.joseangelmaneiro.movies.presentation.model.MovieModel

interface MovieListView : BaseView {
  fun showMovies(movieModels: List<MovieModel>)
  fun cancelRefreshDialog()
  fun navigateToDetailScreen(movieId: Int)
}