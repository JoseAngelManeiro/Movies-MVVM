package com.joseangelmaneiro.movies.domain

import com.joseangelmaneiro.movies.domain.model.Movie
import kotlin.Exception

interface MoviesRepository {
  fun getMovies(onlyOnline: Boolean): Either<Exception, List<Movie>>
  fun getMovie(movieId: Int): Either<Exception, Movie>
}