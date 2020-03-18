package com.joseangelmaneiro.movies.data.source.remote

import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.data.exception.NetworkConnectionException
import com.joseangelmaneiro.movies.data.exception.ServiceException
import com.joseangelmaneiro.movies.domain.Either
import java.io.IOException

// TODO Put here your api key (https://developers.themoviedb.org/3/getting-started)
private const val API_KEY = ""

class MoviesRemoteDataSourceImpl(
  private val movieService: MovieService
) : MoviesRemoteDataSource {

  override fun getMovies(): Either<Exception, List<MovieEntity>> {
    return try {
      val response = movieService.getMovies(API_KEY).execute()
      if (response.isSuccessful) {
        Either.right(response.body().movies)
      } else {
        Either.left(ServiceException())
      }
    } catch (exception: IOException) {
      Either.left(NetworkConnectionException())
    }
  }
}