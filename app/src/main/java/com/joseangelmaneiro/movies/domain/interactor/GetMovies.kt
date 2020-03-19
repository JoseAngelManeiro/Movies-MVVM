package com.joseangelmaneiro.movies.domain.interactor

import com.joseangelmaneiro.movies.domain.Either
import com.joseangelmaneiro.movies.domain.model.Movie
import com.joseangelmaneiro.movies.domain.MoviesRepository

class GetMovies(
  private val repository: MoviesRepository
) : Interactor<GetMovies.Request, List<Movie>> {

  data class Request(val isOnlyOnline: Boolean)

  override fun invoke(request: Request): Either<Exception, List<Movie>> {
    return repository.getMovies(request.isOnlyOnline)
  }
}

