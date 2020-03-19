package com.joseangelmaneiro.movies.domain.interactor

import com.joseangelmaneiro.movies.domain.Either
import com.joseangelmaneiro.movies.domain.model.Movie
import com.joseangelmaneiro.movies.domain.MoviesRepository

class GetMovie(
  private val repository: MoviesRepository
) : Interactor<GetMovie.Request, Movie> {

  data class Request(val movieId: Int)

  override fun invoke(request: Request): Either<Exception, Movie> {
    return repository.getMovie(request.movieId)
  }
}

