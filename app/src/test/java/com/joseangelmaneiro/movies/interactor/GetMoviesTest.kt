package com.joseangelmaneiro.movies.interactor

import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.joseangelmaneiro.movies.domain.interactor.GetMovies
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetMoviesTest {

  private lateinit var getMovies: GetMovies

  @Mock
  lateinit var repository: MoviesRepository

  @Before
  @Throws(Exception::class)
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    getMovies = GetMovies(repository)
  }

  @Test
  fun `should invokes repository`() {
    val request = GetMovies.Request(true)

    getMovies(request)

    verify(repository).getMovies(true)
  }
}