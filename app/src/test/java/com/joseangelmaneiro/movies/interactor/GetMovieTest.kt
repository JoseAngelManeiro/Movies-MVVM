package com.joseangelmaneiro.movies.domain.interactor

import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val MOVIE_ID = 1234

class GetMovieTest {

  lateinit var sut: GetMovie

  @Mock
  lateinit var repository: MoviesRepository

  private val testObserver = TestObserver<Movie>()


  @Before
  @Throws(Exception::class)
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    sut = GetMovie(repository, mock(), mock())
  }

  @Test
  fun useCaseInvokesTheRepositoryAndReturnsAMovie() {
    val repositoryResponse = TestUtils.createMovie()
    whenever(repository.getMovie(MOVIE_ID)).thenReturn(repositoryResponse)

    sut.buildUseCaseObservable(GetMovie.Params(MOVIE_ID)).subscribe(testObserver)

    testObserver.assertValue(repositoryResponse)
  }

}