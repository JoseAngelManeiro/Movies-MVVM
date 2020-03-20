package com.joseangelmaneiro.movies.presentation

import com.joseangelmaneiro.movies.domain.model.Movie

interface MovieListView : BaseView {
  fun showMovies(movies: List<Movie>)
  fun cancelRefreshDialog()
  fun navigateToDetailScreen(movieId: Int)
}