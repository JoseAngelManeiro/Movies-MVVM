package com.joseangelmaneiro.movies.data.source.remote

import com.joseangelmaneiro.movies.data.entity.MovieEntity


interface MoviesRemoteDataSource {

  @Throws(Exception::class)
  fun getMovies(): List<MovieEntity>

}