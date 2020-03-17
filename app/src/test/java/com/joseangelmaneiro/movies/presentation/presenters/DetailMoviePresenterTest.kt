package com.joseangelmaneiro.movies.presentation.presenters

import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.Observer
import com.joseangelmaneiro.movies.domain.interactor.UseCase
import com.joseangelmaneiro.movies.domain.interactor.GetMovie
import com.joseangelmaneiro.movies.domain.interactor.UseCaseFactory
import com.joseangelmaneiro.movies.presentation.DetailMovieView
import com.joseangelmaneiro.movies.presentation.formatters.Formatter
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val MOVIE_ID = 1234
private const val RELEASE_DATE = "22/10/2017"
private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500fake_poster_path.png"

class DetailMoviePresenterTest {

    @Mock
    lateinit var useCaseFactory: UseCaseFactory
    @Mock
    lateinit var useCase: UseCase<Movie, GetMovie.Params>
    @Mock
    lateinit var formatter: Formatter
    @Mock
    lateinit var view: DetailMovieView

    val paramsCaptor = argumentCaptor<GetMovie.Params>()
    val observerCaptor = argumentCaptor<Observer<Movie>>()

    lateinit var sut: DetailMoviePresenter

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        sut = DetailMoviePresenter(useCaseFactory, formatter,
            MOVIE_ID
        )
        sut.setView(view)

        whenever(useCaseFactory.getMovie()).thenReturn(useCase)
        whenever(useCase.execute(any(), any())).thenReturn(mock())
    }

    @Test
    fun viewReady_InvokesUseCase() {
        sut.viewReady()

        verify(useCase).execute(any(), paramsCaptor.capture())
        assertEquals(MOVIE_ID, paramsCaptor.firstValue.movieId)
    }

    @Test
    fun displayValuesWhenUseReturnsAMovie() {
        val movie = whenUseCaseReturnsAMovie()

        thenDisplayMovieValues(movie)
    }

    private fun thenDisplayMovieValues(movie: Movie) {
        verify(view).displayImage(IMAGE_URL)
        verify(view).displayTitle(movie.title)
        verify(view).displayVoteAverage(movie.voteAverage)
        verify(view).displayReleaseDate(RELEASE_DATE)
        verify(view).displayOverview(movie.overview)
    }

    @Test
    fun navUpSelected_InvokesGoToBack() {
        sut.navUpSelected()

        verify(view).goToBack()
    }

    private fun whenUseCaseReturnsAMovie(): Movie {
        val movie = TestUtils.createMovie()
        whenever(formatter.getCompleteUrlImage(movie.backdropPath)).thenReturn(IMAGE_URL)
        whenever(formatter.formatDate(movie.releaseDate)).thenReturn(RELEASE_DATE)

        sut.viewReady()

        verify(useCase).execute(observerCaptor.capture(), any())
        observerCaptor.firstValue.onSuccess(movie)
        return movie
    }
}
