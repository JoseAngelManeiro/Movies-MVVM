package com.joseangelmaneiro.movies.presentation

import com.joseangelmaneiro.movies.domain.model.Movie
import com.joseangelmaneiro.movies.presentation.model.MovieModel

class MovieModelFactory {

  fun createMovieModels(movies: List<Movie>): List<MovieModel> {
    return movies.map {
      MovieModel(id = it.id, posterPath = it.posterPath)
    }
  }
}