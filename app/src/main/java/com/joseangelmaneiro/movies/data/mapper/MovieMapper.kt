package com.joseangelmaneiro.movies.data.mapper

import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.domain.model.Movie
import java.text.SimpleDateFormat
import java.util.*

private const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500"
private const val SERVER_DATE_FORMAT = "yyyy-MM-dd"

// Mapper class used to transform MovieEntity, in the data layer, to Movie, in the domain layer.
class MovieMapper {

  fun transform(movieEntity: MovieEntity?): Movie? {
    var movie: Movie? = null
    if (movieEntity != null) {
      movie = Movie(
        movieEntity.id,
        movieEntity.voteAverage,
        movieEntity.title,
        completeURL(movieEntity.posterPath)!!,
        completeURL(movieEntity.backdropPath),
        movieEntity.overview,
        getDate(movieEntity.releaseDate)
      )
    }
    return movie
  }

  fun transform(movieEntityList: List<MovieEntity>): List<Movie> {
    return movieEntityList.map { transform(it)!! }
  }

  private fun completeURL(path: String?): String? {
    return if (path != null) BASE_URL_IMAGE + path else null
  }

  private fun getDate(stringDate: String): Date {
    val formatter = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US)
    return formatter.parse(stringDate)
  }
}