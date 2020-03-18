package com.joseangelmaneiro.movies.domain.interactor

import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.joseangelmaneiro.movies.domain.executor.JobScheduler
import com.joseangelmaneiro.movies.domain.executor.UIScheduler
import javax.inject.Inject


class UseCaseFactory @Inject constructor(
  private val repository: MoviesRepository,
  private val uiScheduler: UIScheduler,
  private val jobScheduler: JobScheduler
) {

  fun getMovie(): UseCase<Movie, GetMovie.Params> {
    return GetMovie(repository, uiScheduler, jobScheduler)
  }

  fun getMovies(): UseCase<List<Movie>, GetMovies.Params> {
    return GetMovies(repository, uiScheduler, jobScheduler)
  }

}