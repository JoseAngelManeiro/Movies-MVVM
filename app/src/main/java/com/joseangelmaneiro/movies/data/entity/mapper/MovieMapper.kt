package com.joseangelmaneiro.movies.data.entity.mapper

import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.domain.Movie
import javax.inject.Inject

// Mapper class used to transform MovieEntity, in the data layer, to Movie, in the domain layer.
class MovieMapper @Inject constructor() {

  fun transform(movieEntity: MovieEntity?): Movie? {
    var movie: Movie? = null
    if (movieEntity != null) {
      movie = Movie(
        movieEntity.id,
        movieEntity.voteAverage,
        movieEntity.title,
        movieEntity.posterPath,
        movieEntity.backdropPath,
        movieEntity.overview,
        movieEntity.releaseDate
      )
    }
    return movie
  }

  fun transform(movieEntityList: List<MovieEntity>): List<Movie> {
    return movieEntityList.map { transform(it)!! }
  }
}