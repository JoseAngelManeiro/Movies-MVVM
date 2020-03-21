package com.joseangelmaneiro.movies.platform.features.detail

import com.joseangelmaneiro.movies.domain.model.Movie
import com.joseangelmaneiro.movies.platform.features.detail.MovieDetailModel

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