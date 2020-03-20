package com.joseangelmaneiro.movies.presentation

import com.joseangelmaneiro.movies.domain.model.Movie

interface DetailMovieView : BaseView {
  fun displayMovie(movie: Movie)
  fun goToBack()
}