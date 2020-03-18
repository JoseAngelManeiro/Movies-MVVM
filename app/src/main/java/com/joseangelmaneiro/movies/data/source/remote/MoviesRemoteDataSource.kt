package com.joseangelmaneiro.movies.data.source.remote

import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.domain.Either

interface MoviesRemoteDataSource {
  fun getMovies(): Either<Exception, List<MovieEntity>>
}