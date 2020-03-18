package com.joseangelmaneiro.movies.domain.interactor

import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.joseangelmaneiro.movies.domain.executor.JobScheduler
import com.joseangelmaneiro.movies.domain.executor.UIScheduler
import io.reactivex.Single

class GetMovie(
  private val repository: MoviesRepository,
  uiScheduler: UIScheduler,
  jobScheduler: JobScheduler
) : UseCase<Movie, GetMovie.Params>(uiScheduler, jobScheduler) {

  override fun buildUseCaseObservable(params: Params): Single<Movie> {
    return Single.create { emitter ->
      repository.getMovie(params.movieId).fold(
        {
          if (!emitter.isDisposed) {
            emitter.onError(it)
          }
        },
        {
          emitter.onSuccess(it)
        }
      )
    }
  }

  class Params(val movieId: Int)
}

