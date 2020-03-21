package com.joseangelmaneiro.movies.platform.features.list

import com.joseangelmaneiro.movies.domain.model.Movie

class MovieModelFactory {

  fun createMovieModels(movies: List<Movie>): List<MovieModel> {
    return movies.map {
      MovieModel(
        id = it.id,
        posterPath = it.posterPath
      )
    }
  }
}