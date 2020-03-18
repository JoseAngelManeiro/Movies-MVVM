package com.joseangelmaneiro.movies.data.source.remote

import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.data.exception.NetworkConnectionException
import com.joseangelmaneiro.movies.data.exception.ServiceException
import java.io.IOException

// TODO Put here your api key (https://developers.themoviedb.org/3/getting-started)
private const val API_KEY = ""

class MoviesRemoteDataSourceImpl(private val movieService: MovieService) : MoviesRemoteDataSource {

  override fun getMovies(): List<MovieEntity> {
    try {
      val response = movieService.getMovies(API_KEY).execute()
      if (response.isSuccessful) {
        return response.body().movies;
      } else {
        throw ServiceException()
      }
    } catch (exception: IOException) {
      throw NetworkConnectionException()
    }
  }

}