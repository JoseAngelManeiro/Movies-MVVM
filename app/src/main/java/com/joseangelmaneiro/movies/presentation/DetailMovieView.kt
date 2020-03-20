package com.joseangelmaneiro.movies.presentation

import com.joseangelmaneiro.movies.presentation.model.MovieDetailModel

interface DetailMovieView : BaseView {
  fun displayMovieDetailModel(movieDetailModel: MovieDetailModel)
  fun goToBack()
}