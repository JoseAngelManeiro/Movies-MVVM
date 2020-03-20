package com.joseangelmaneiro.movies.presentation

import com.joseangelmaneiro.movies.domain.model.Movie
import com.joseangelmaneiro.movies.presentation.model.MovieDetailModel

class MovieDetailModelFactory {

  fun createMovieDetailModel(movie: Movie): MovieDetailModel {
    return MovieDetailModel(
      voteAverage = movie.voteAverage,
      title = movie.title,
      backdropPath = movie.backdropPath,
      overview = movie.overview,
      releaseDate = movie.releaseDate
    )
  }
}