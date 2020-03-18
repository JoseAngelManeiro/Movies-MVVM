package com.joseangelmaneiro.movies.domain.interactor

import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.joseangelmaneiro.movies.domain.executor.JobScheduler
import com.joseangelmaneiro.movies.domain.executor.UIScheduler
import io.reactivex.Single

class GetMovies(
  private val repository: MoviesRepository,
  uiScheduler: UIScheduler,
  jobScheduler: JobScheduler
) : UseCase<List<Movie>, GetMovies.Params>(uiScheduler, jobScheduler) {

  override fun buildUseCaseObservable(params: Params): Single<List<Movie>> {
    return Single.create { emitter ->
      repository.getMovies(params.isOnlyOnline).fold(
        {
          if (!emitter.isDisposed) {
            emitter.onError(it)
          }
        },
        {
          emitter.onSuccess(it)
        })
    }
  }

  class Params(val isOnlyOnline: Boolean)
}

