package com.joseangelmaneiro.movies.presentation.presenters

import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.domain.Either
import com.joseangelmaneiro.movies.domain.interactor.GetMovies
import com.joseangelmaneiro.movies.platform.executor.SyncInteractorExecutor
import com.joseangelmaneiro.movies.presentation.MovieListView
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MovieListPresenterTest {

  @Mock
  lateinit var getMovies: GetMovies
  @Mock
  lateinit var view: MovieListView

  private val paramsCaptor = argumentCaptor<GetMovies.Request>()

  private lateinit var presenter: MovieListPresenter

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    presenter = MovieListPresenter(
      executor = SyncInteractorExecutor(),
      getMovies = getMovies
    )
    presenter.setView(view)
  }

  @Test
  fun `should invokes interactor without refresh mode when view is ready`() {
    val isOnlyOnline = false
    whenever(getMovies.invoke(GetMovies.Request(isOnlyOnline)))
      .thenReturn(Either.right(TestUtils.createDefaultMovieList()))

    presenter.viewReady()

    verify(getMovies).invoke(paramsCaptor.capture())
    assertFalse(paramsCaptor.firstValue.isOnlyOnline)
  }

  @Test
  fun `should invokes interactor with refresh mode when refresh is called`() {
    val isOnlyOnline = true
    whenever(getMovies.invoke(GetMovies.Request(isOnlyOnline)))
      .thenReturn(Either.right(TestUtils.createDefaultMovieList()))

    presenter.refresh()

    verify(getMovies).invoke(paramsCaptor.capture())
    assertTrue(paramsCaptor.firstValue.isOnlyOnline)
  }

  @Test
  fun `should shows movies when interactor returns correct data`() {
    val movies = TestUtils.createDefaultMovieList()
    whenever(getMovies.invoke(any())).thenReturn(Either.right(movies))

    presenter.viewReady()

    verify(view).cancelRefreshDialog()
    verify(view).showMovies(movies)
  }

  @Test
  fun `should show error message when interactor returns an exception`() {
    whenever(getMovies.invoke(any())).thenReturn(Either.left(Exception("Fake error")))

    presenter.viewReady()

    verify(view).cancelRefreshDialog()
    verify(view).showErrorMessage("Fake error")
  }
}
