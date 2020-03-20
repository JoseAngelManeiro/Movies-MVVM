package com.joseangelmaneiro.movies.presentation.presenters

import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.domain.Either
import com.joseangelmaneiro.movies.domain.interactor.GetMovie
import com.joseangelmaneiro.movies.platform.executor.SyncInteractorExecutor
import com.joseangelmaneiro.movies.presentation.DetailMovieView
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val MOVIE_ID = 1234

class DetailMoviePresenterTest {

  @Mock
  lateinit var getMovie: GetMovie
  @Mock
  lateinit var view: DetailMovieView

  private lateinit var presenter: DetailMoviePresenter

  @Before
  @Throws(Exception::class)
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    presenter = DetailMoviePresenter(
      executor = SyncInteractorExecutor(),
      getMovie = getMovie
    )
    presenter.setView(view)
  }

  @Test
  fun `should display movie values`() {
    val movie = TestUtils.createMovie()
    whenever(getMovie.invoke(GetMovie.Request(MOVIE_ID))).thenReturn(Either.right(movie))

    presenter.viewReady(MOVIE_ID)

    verify(view).displayMovie(movie)
  }

  @Test
  fun `should show error message when interactor returns an exception`() {
    whenever(getMovie.invoke(GetMovie.Request(MOVIE_ID)))
      .thenReturn(Either.left(Exception("Fake error")))

    presenter.viewReady(MOVIE_ID)

    verify(view).showErrorMessage("Fake error")
  }

  @Test
  fun `should go to back when navUp is selected`() {
    presenter.navUpSelected()

    verify(view).goToBack()
  }
}
