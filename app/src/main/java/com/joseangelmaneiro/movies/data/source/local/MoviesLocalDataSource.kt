package com.joseangelmaneiro.movies.data.source.local

import com.joseangelmaneiro.movies.data.entity.MovieEntity

interface MoviesLocalDataSource {
  fun getMovies(): List<MovieEntity>
  fun getMovie(movieId: Int): MovieEntity?
  fun saveMovies(movieEntityList: List<MovieEntity>)
  fun deleteAllMovies()
}