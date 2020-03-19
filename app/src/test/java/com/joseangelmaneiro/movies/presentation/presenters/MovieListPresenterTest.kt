package com.joseangelmaneiro.movies.presentation.presenters

import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.domain.Either
import com.joseangelmaneiro.movies.domain.model.Movie
import com.joseangelmaneiro.movies.domain.interactor.GetMovies
import com.joseangelmaneiro.movies.platform.executor.SyncInteractorExecutor
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

private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500fake_poster_path.png"

class MovieListPresenterTest {

  @Mock
  lateinit var getMovies: GetMovies
  @Mock
  lateinit var view: MovieListView
  @Mock
  lateinit var cellView: MovieCellView
  @Mock
  lateinit var formatter: Formatter

  private val paramsCaptor = argumentCaptor<GetMovies.Request>()

  private lateinit var presenter: MovieListPresenter

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    presenter = MovieListPresenter(
      executor = SyncInteractorExecutor(),
      getMovies = getMovies,
      formatter = formatter
    )
    presenter.setView(view)
  }

  @Test
  fun `should invokes interactor without refresh mode when view is ready`() {
    whenever(getMovies.invoke(GetMovies.Request(false)))
      .thenReturn(Either.right(TestUtils.createDefaultMovieList()))

    presenter.viewReady()

    verify(getMovies).invoke(paramsCaptor.capture())
    assertFalse(paramsCaptor.firstValue.isOnlyOnline)
  }

  @Test
  fun `should invokes interactor with refresh mode when refresh is called`() {
    whenever(getMovies.invoke(GetMovies.Request(true)))
      .thenReturn(Either.right(TestUtils.createDefaultMovieList()))

    presenter.refresh()

    verify(getMovies).invoke(paramsCaptor.capture())
    assertTrue(paramsCaptor.firstValue.isOnlyOnline)
  }

  @Test
  fun `should save result and refresh view when interactor returns correct data`() {
    whenever(getMovies.invoke(any()))
      .thenReturn(Either.right(TestUtils.createDefaultMovieList()))

    presenter.viewReady()

    assertFalse(presenter.moviesListIsEmpty())
    verify(view).cancelRefreshDialog()
    verify(view).refreshList()
  }

  @Test
  fun `should show error message when interactor returns an exception`() {
    whenever(getMovies.invoke(any()))
      .thenReturn(Either.left(Exception("Fake error")))

    presenter.viewReady()

    verify(view).cancelRefreshDialog()
    verify(view).showErrorMessage("Fake error")
  }

  @Test
  fun `should returns 0 when there is no data and getItemsCount is called`() {
    assertEquals(0, presenter.getItemsCount())
  }

  @Test
  fun `should returns the current items number when getItemsCount is called`() {
    val movieList = TestUtils.createDefaultMovieList()

    presenter.saveMovies(movieList)

    assertThat(presenter.getItemsCount(), IsEqual(movieList.size))
  }

  @Test
  fun `should displays image when configureCell is called`() {
    val positionSelected = 1
    val movie = givenAMovieFromSavedList(positionSelected)
    whenever(formatter.getCompleteUrlImage(movie.posterPath))
      .thenReturn(IMAGE_URL)

    presenter.configureCell(cellView, positionSelected)

    verify(cellView).displayImage(IMAGE_URL)
  }

  @Test
  fun `should saves movie id and navigates to detail screen when onItemClick is called`() {
    val positionSelected = 1
    val movie = givenAMovieFromSavedList(positionSelected)

    presenter.onItemClick(positionSelected)

    assertThat(presenter.getSelectedMovieId(), IsEqual(movie.id))
    verify(view).navigateToDetailScreen(movie.id)
  }

  private fun givenAMovieFromSavedList(position: Int): Movie {
    val movieList = TestUtils.createDefaultMovieList()
    presenter.saveMovies(movieList)
    return movieList[position]
  }
}
