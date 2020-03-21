package com.joseangelmaneiro.movies.domain.interactor

import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetMovieTest {

  private lateinit var getMovie: GetMovie

  @Mock
  lateinit var repository: MoviesRepository

  @Before
  @Throws(Exception::class)
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    getMovie = GetMovie(repository)
  }

  @Test
  fun `should invokes repository`() {
    val request = GetMovie.Request(1234)

    getMovie(request)

    verify(repository).getMovie(1234)
  }
}