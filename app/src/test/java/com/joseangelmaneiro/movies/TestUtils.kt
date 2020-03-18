package com.joseangelmaneiro.movies

import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.domain.Movie
import java.util.ArrayList

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
private const val RELEASE_DATE = "05/12/2017"

const val DEFAULT_SIZE_LIST = 10

class TestUtils {

  companion object {

    fun createMovieEntity(): MovieEntity {
      return MovieEntity(
        VOTE_COUNT, MOVIE_ID, VIDEO, VOTE_AVERAGE, TITLE, POPULARITY,
        POSTER_PATH, ORIGINAL_LANGUAGE, ORIGINAL_TITLE, GENRE_IDS, BACKDROPPATH, ADULT,
        OVERVIEW, RELEASE_DATE
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
          ADULT, OVERVIEW, RELEASE_DATE
        )
        movies.add(movie)
      }
      return movies
    }

    fun createMovie(): Movie {
      return Movie(
        MOVIE_ID, VOTE_AVERAGE, TITLE, POSTER_PATH, BACKDROPPATH, OVERVIEW,
        RELEASE_DATE
      )
    }

    fun createDefaultMovieList(): List<Movie> {
      return createMovieList(DEFAULT_SIZE_LIST)
    }

    fun createMovieList(numMovies: Int): List<Movie> {
      val movies = mutableListOf<Movie>()
      for (i in 0 until numMovies) {
        val movie = Movie(
          i, VOTE_AVERAGE, TITLE, POSTER_PATH, BACKDROPPATH, OVERVIEW,
          RELEASE_DATE
        )
        movies.add(movie)
      }
      return movies
    }

  }

}