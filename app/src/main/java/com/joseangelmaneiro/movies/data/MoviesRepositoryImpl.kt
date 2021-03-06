package com.joseangelmaneiro.movies.data

import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.data.mapper.MovieMapper
import com.joseangelmaneiro.movies.data.exception.ElementNotFoundException
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSource
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSource
import com.joseangelmaneiro.movies.domain.Either
import com.joseangelmaneiro.movies.domain.model.Movie
import com.joseangelmaneiro.movies.domain.MoviesRepository

class MoviesRepositoryImpl(
  private val localDataSource: MoviesLocalDataSource,
  private val remoteDataSource: MoviesRemoteDataSource,
  private val movieMapper: MovieMapper
) : MoviesRepository {

  override fun getMovies(onlyOnline: Boolean): Either<Exception, List<Movie>> {
    return object : PrefetchLocalData<List<MovieEntity>, List<Movie>>() {
      override fun loadFromLocal() = movieMapper.transform(localDataSource.getMovies())

      override fun shouldFetch(data: List<Movie>?) = (onlyOnline || data.isNullOrEmpty())

      override fun loadFromService() = remoteDataSource.getMovies()

      override fun saveServiceResult(item: List<MovieEntity>) = saveData(item)
    }.load()
  }

  private fun saveData(movieEntityList: List<MovieEntity>) {
    localDataSource.deleteAllMovies()
    localDataSource.saveMovies(movieEntityList)
  }

  override fun getMovie(movieId: Int): Either<Exception, Movie> {
    val movieEntity = localDataSource.getMovie(movieId)
    return if (movieEntity == null) {
      Either.left(ElementNotFoundException())
    } else {
      Either.right(movieMapper.transform(movieEntity)!!)
    }
  }
}