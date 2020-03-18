package com.joseangelmaneiro.movies.domain

import kotlin.Exception

interface MoviesRepository {
  fun getMovies(onlyOnline: Boolean): Either<Exception, List<Movie>>
  fun getMovie(movieId: Int): Either<Exception, Movie>
}