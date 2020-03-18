package com.joseangelmaneiro.movies.data

import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.data.entity.mapper.MovieMapper
import com.joseangelmaneiro.movies.data.exception.NetworkConnectionException
import com.joseangelmaneiro.movies.data.exception.ServiceException
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSource
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSource
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Test

const val MOVIE_ID = 1234

class MoviesRepositoryImplTest {

    private val movieEntityList = TestUtils.createDefaultMovieEntityList()
    private val movieEntity = TestUtils.createMovieEntity()
    private val movieList = TestUtils.createDefaultMovieList()
    private val movie = TestUtils.createMovie()

    private lateinit var sut: MoviesRepositoryImpl

    @Mock
    private lateinit var localDataSource: MoviesLocalDataSource
    @Mock
    private lateinit var remoteDataSource: MoviesRemoteDataSource
    @Mock
    private lateinit var movieMapper: MovieMapper


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = MoviesRepositoryImpl(localDataSource, remoteDataSource, movieMapper)
    }

    @Test
    fun getMovies_ReturnsMoviesFromRemoteDataSource() {
        givenMoviesFromRemote(movieEntityList)
        whenever(movieMapper.transform(movieEntityList)).thenReturn(movieList)

        val response = sut.getMovies(true)

        verify(localDataSource).deleteAllMovies()
        verify(localDataSource).saveMovies(movieEntityList)
        assertThat(response, IsEqual(movieList))
    }

    @Test
    fun getMovies_ReturnsMoviesFromLocalDataSource() {
        givenMoviesFromLocal(movieEntityList)
        whenever(movieMapper.transform(movieEntityList)).thenReturn(movieList)

        val response = sut.getMovies(false)

        assertThat(response, IsEqual(movieList))
    }

    @Test
    fun getMovies_ReturnsEmptyListFromLocalDataSource() {
        givenMoviesFromLocal(emptyList())
        givenMoviesFromRemote(movieEntityList)
        whenever(movieMapper.transform(movieEntityList)).thenReturn(movieList)

        val response = sut.getMovies(false)

        verify(localDataSource).deleteAllMovies()
        verify(localDataSource).saveMovies(movieEntityList)
        assertThat(response, IsEqual(movieList))
    }

    @Test(expected = ServiceException::class)
    @Throws(Exception::class)
    fun getMovies_ReturnsServiceExceptionFromRemoteDataSource() {
        givenExceptionFromRemote(ServiceException())

        sut.getMovies(true)
    }

    @Test(expected = NetworkConnectionException::class)
    fun getMovies_ReturnsNetworkConnectionExceptionFromRemoteDataSource() {
        givenExceptionFromRemote(NetworkConnectionException())

        sut.getMovies(true)
    }

    @Test
    fun getMovie_ReturnsMovieFromLocalDataSource() {
        whenever(localDataSource.getMovie(MOVIE_ID)).thenReturn(movieEntity)
        whenever(movieMapper.transform(movieEntity)).thenReturn(movie)

        val response = sut.getMovie(MOVIE_ID)

        assertThat(response, IsEqual(movie))
    }

    private fun givenMoviesFromRemote(movies: List<MovieEntity>) {
        `when`(remoteDataSource.getMovies()).thenReturn(movies)
    }

    private fun givenMoviesFromLocal(movies: List<MovieEntity>) {
        `when`(localDataSource.getMovies()).thenReturn(movies)
    }

    private fun givenExceptionFromRemote(exception: Exception) {
        `when`(remoteDataSource.getMovies()).thenThrow(exception)
    }

}
