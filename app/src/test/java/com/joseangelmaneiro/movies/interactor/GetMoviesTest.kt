package com.joseangelmaneiro.movies.domain.interactor

import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.data.exception.ServiceException
import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class GetMoviesTest{

    lateinit var sut: GetMovies

    @Mock
    lateinit var repository: MoviesRepository

    private val testObserver = TestObserver<List<Movie>>()


    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        sut = GetMovies(repository, mock(), mock())
    }

    @Test
    fun useCaseInvokesTheRepositoryAndReturnsAListOfMovies() {
        val repositoryResponse = TestUtils.createDefaultMovieList()
        whenever(repository.getMovies(any())).thenReturn(repositoryResponse)

        sut.buildUseCaseObservable(GetMovies.Params(any())).subscribe(testObserver)

        testObserver.assertValue(repositoryResponse)
    }

    @Test
    fun useCaseInvokesTheRepositoryAndFiresAException() {
        val repositoryException = ServiceException()
        whenever(repository.getMovies(any())).thenThrow(repositoryException)

        sut.buildUseCaseObservable(GetMovies.Params(any())).subscribe(testObserver)

        testObserver.assertError(repositoryException)
    }

}