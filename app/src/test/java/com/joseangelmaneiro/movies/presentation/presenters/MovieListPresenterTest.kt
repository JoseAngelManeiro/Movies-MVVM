package com.joseangelmaneiro.movies.presentation.presenters

import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.Observer
import com.joseangelmaneiro.movies.domain.interactor.GetMovies
import com.joseangelmaneiro.movies.domain.interactor.UseCase
import com.joseangelmaneiro.movies.domain.interactor.UseCaseFactory
import com.joseangelmaneiro.movies.presentation.MovieCellView
import com.joseangelmaneiro.movies.presentation.MovieListView
import com.joseangelmaneiro.movies.presentation.formatters.Formatter
import com.nhaarman.mockitokotlin2.*
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val POSITION = 0
private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500fake_poster_path.png"

class MovieListPresenterTest {

    @Mock
    lateinit var useCaseFactory: UseCaseFactory
    @Mock
    lateinit var useCase: UseCase<List<Movie>, GetMovies.Params>
    @Mock
    lateinit var view: MovieListView
    @Mock
    lateinit var cellView: MovieCellView
    @Mock
    lateinit var formatter: Formatter

    val observerCaptor = argumentCaptor<Observer<List<Movie>>>()
    val paramsCaptor =  argumentCaptor<GetMovies.Params>()

    lateinit var sut: MovieListPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        sut = MovieListPresenter(useCaseFactory, formatter)
        sut.setView(view)

        whenever(useCaseFactory.getMovies()).thenReturn(useCase)
        whenever(useCase.execute(any(), any())).thenReturn(mock())
    }

    @Test
    fun viewReady_InvokesUseCase() {
        sut.viewReady()

        verify(useCase).execute(any(), paramsCaptor.capture())
        assertFalse(paramsCaptor.firstValue.isOnlyOnline)
    }

    @Test
    fun refresh_InvokesUseCase() {
        sut.refresh()

        verify(useCase).execute(any(), paramsCaptor.capture())
        assertTrue(paramsCaptor.firstValue.isOnlyOnline)
    }

    @Test
    fun saveResultAndRefreshViewWhenUseCaseReturnsAMovieList() {
        whenUseCaseReturnsAMovieList(TestUtils.createDefaultMovieList())

        assertFalse(sut.moviesListIsEmpty())
        verify(view).cancelRefreshDialog()
        verify(view).refreshList()
    }

    @Test
    fun showErrorMessageWhenUseCaseFiresAException() {
        whenUseCaseFiresAException(Exception("Fake error"))

        verify(view).cancelRefreshDialog()
        verify(view).showErrorMessage("Fake error")
    }

    @Test
    fun getItemsCount_ReturnsZeroWhenThereIsNoData() {
        assertEquals(0, sut.getItemsCount())
    }

    @Test
    fun getItemsCount_ReturnsTheNumberOfItems() {
        val movieList = givenASavedMovieList()

        assertThat(sut.getItemsCount(), IsEqual(movieList.size))
    }

    @Test
    fun configureCell_DisplaysImage() {
        val (_, _, _, posterPath) = givenAMovieFromSavedList(POSITION)
        whenever(formatter.getCompleteUrlImage(posterPath)).thenReturn(IMAGE_URL)

        sut.configureCell(cellView, POSITION)

        verify(cellView).displayImage(IMAGE_URL)
    }

    @Test
    fun onItemClick_SavesSelectedMovieIdAndInvokesNavigateToDetailScreen() {
        val (id) = givenAMovieFromSavedList(POSITION)

        sut.onItemClick(POSITION)

        assertThat(sut.getSelectedMovieId(), IsEqual(id))
        verify(view).navigateToDetailScreen(id)
    }

    private fun whenUseCaseReturnsAMovieList(movies: List<Movie>) {
        sut.viewReady()

        verify(useCase).execute(observerCaptor.capture(), any())
        observerCaptor.firstValue.onSuccess(movies)
    }

    private fun whenUseCaseFiresAException(exception: Exception) {
        sut.viewReady()

        verify(useCase).execute(observerCaptor.capture(), any())
        observerCaptor.firstValue.onError(exception)
    }

    private fun givenASavedMovieList(): List<Movie> {
        val movieList = TestUtils.createDefaultMovieList()
        sut.saveMovies(movieList)
        return movieList
    }

    private fun givenAMovieFromSavedList(position: Int): Movie {
        return givenASavedMovieList()[position]
    }
}
