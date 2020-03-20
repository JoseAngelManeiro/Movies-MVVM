package com.joseangelmaneiro.movies

import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.domain.model.Movie
import java.util.*

private const val MOVIE_ID = 1
private const val VOTE_COUNT = 5
private const val VIDEO = true
private const val VOTE_AVERAGE = "6.2"
private const val TITLE = "Movie One"
private const val POPULARITY = 5f
private const val POSTER_PATH = "fake_poster_path.png"
private const val ORIGINAL_LANGUAGE = "ES"
private const val ORIGINAL_TITLE = "main_title"
private val GENRE_IDS: List<Int>? = null
private const val BACKDROPPATH = "fake_backdroppath.png"
private const val ADULT = false
private const val OVERVIEW = "Overview"
private val RELEASE_DATE = GregorianCalendar(2017, 12, 5).time

const val DEFAULT_SIZE_LIST = 10

class TestUtils {

  companion object {

    fun createMovieEntity(): MovieEntity {
      return MovieEntity(
        VOTE_COUNT, MOVIE_ID, VIDEO, VOTE_AVERAGE, TITLE, POPULARITY,
        POSTER_PATH, ORIGINAL_LANGUAGE, ORIGINAL_TITLE, GENRE_IDS, BACKDROPPATH, ADULT,
        OVERVIEW, "05/12/2017"
      )
    }

    fun createDefaultMovieEntityList(): List<MovieEntity> {
      return createMovieEntityList(DEFAULT_SIZE_LIST)
    }

    fun createMovieEntityList(numMovies: Int): List<MovieEntity> {
      val movies = ArrayList<MovieEntity>()
      for (i in 0 until numMovies) {
        val movie = MovieEntity(
          VOTE_COUNT, i, VIDEO, VOTE_AVERAGE, TITLE, POPULARITY,
          POSTER_PATH, ORIGINAL_LANGUAGE, ORIGINAL_TITLE, GENRE_IDS, BACKDROPPATH,
          ADULT, OVERVIEW, "05/12/2017"
        )
        movies.add(movie)
      }
      return movies
    }

    fun createMovie(id: Int = MOVIE_ID): Movie {
      return Movie(
        id,
        VOTE_AVERAGE,
        TITLE,
        POSTER_PATH,
        BACKDROPPATH,
        OVERVIEW,
        RELEASE_DATE
      )
    }

    fun createDefaultMovieList(): List<Movie> {
      return createMovieList(DEFAULT_SIZE_LIST)
    }

    fun createMovieList(numMovies: Int): List<Movie> {
      val movies = mutableListOf<Movie>()
      for (i in 0 until numMovies) {
        movies.add(createMovie(i))
      }
      return movies
    }
  }
}