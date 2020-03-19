package com.joseangelmaneiro.movies.data

import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.data.mapper.MovieMapper
import com.joseangelmaneiro.movies.data.exception.NetworkConnectionException
import com.joseangelmaneiro.movies.data.exception.ServiceException
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSource
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSource
import com.joseangelmaneiro.movies.domain.Either
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.assertThat
import org.junit.Test

const val MOVIE_ID = 1234

class MoviesRepositoryImplTest {

  private val movieEntityList = TestUtils.createDefaultMovieEntityList()
  private val movieEntity = TestUtils.createMovieEntity()
  private val movieList = TestUtils.createDefaultMovieList()
  private val movie = TestUtils.createMovie()

  private lateinit var moviesRepository: MoviesRepositoryImpl

  @Mock
  private lateinit var localDataSource: MoviesLocalDataSource
  @Mock
  private lateinit var remoteDataSource: MoviesRemoteDataSource
  @Mock
  private lateinit var movieMapper: MovieMapper


  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    moviesRepository = MoviesRepositoryImpl(localDataSource, remoteDataSource, movieMapper)
  }

  @Test
  fun getMovies_ReturnsMoviesFromRemoteDataSource() {
    givenMoviesFromRemote(movieEntityList)
    whenever(movieMapper.transform(movieEntityList)).thenReturn(movieList)

    val response = moviesRepository.getMovies(true)

    verify(localDataSource).deleteAllMovies()
    verify(localDataSource).saveMovies(movieEntityList)
    assertThat(response.rightValue, IsEqual(movieList))
  }

  @Test
  fun getMovies_ReturnsMoviesFromLocalDataSource() {
    givenMoviesFromLocal(movieEntityList)
    whenever(movieMapper.transform(movieEntityList)).thenReturn(movieList)

    val response = moviesRepository.getMovies(false)

    assertThat(response.rightValue, IsEqual(movieList))
  }

  @Test
  fun getMovies_ReturnsEmptyListFromLocalDataSource() {
    givenMoviesFromLocal(emptyList())
    givenMoviesFromRemote(movieEntityList)
    whenever(movieMapper.transform(movieEntityList)).thenReturn(movieList)

    val response = moviesRepository.getMovies(false)

    verify(localDataSource).deleteAllMovies()
    verify(localDataSource).saveMovies(movieEntityList)
    assertThat(response.rightValue, IsEqual(movieList))
  }

  @Test
  fun getMovies_ReturnsServiceExceptionFromRemoteDataSource() {
    givenExceptionFromRemote(ServiceException())

    val response = moviesRepository.getMovies(true)

    assertThat(response.leftValue, IsInstanceOf(ServiceException::class.java))
  }

  @Test
  fun getMovies_ReturnsNetworkConnectionExceptionFromRemoteDataSource() {
    givenExceptionFromRemote(NetworkConnectionException())

    val response = moviesRepository.getMovies(true)

    assertThat(response.leftValue, IsInstanceOf(NetworkConnectionException::class.java))
  }

  @Test
  fun getMovie_ReturnsMovieFromLocalDataSource() {
    whenever(localDataSource.getMovie(MOVIE_ID)).thenReturn(movieEntity)
    whenever(movieMapper.transform(movieEntity)).thenReturn(movie)

    val response = moviesRepository.getMovie(MOVIE_ID)

    assertThat(response.rightValue, IsEqual(movie))
  }

  private fun givenMoviesFromRemote(movies: List<MovieEntity>) {
    `when`(remoteDataSource.getMovies()).thenReturn(Either.right(movies))
  }

  private fun givenMoviesFromLocal(movies: List<MovieEntity>) {
    `when`(localDataSource.getMovies()).thenReturn(movies)
  }

  private fun givenExceptionFromRemote(exception: Exception) {
    `when`(remoteDataSource.getMovies()).thenReturn(Either.left(exception))
  }

}
