package com.joseangelmaneiro.movies.presentation.presenters

import com.joseangelmaneiro.movies.domain.Either
import com.joseangelmaneiro.movies.domain.interactor.GetMovie
import com.joseangelmaneiro.movies.domain.model.Movie
import com.joseangelmaneiro.movies.platform.executor.SyncInteractorExecutor
import com.joseangelmaneiro.movies.presentation.DetailMovieView
import com.joseangelmaneiro.movies.presentation.MovieDetailModelFactory
import com.joseangelmaneiro.movies.presentation.model.MovieDetailModel
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
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
  @Mock
  lateinit var movieDetailModelFactory: MovieDetailModelFactory

  private lateinit var presenter: DetailMoviePresenter

  @Before
  @Throws(Exception::class)
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    presenter = DetailMoviePresenter(
      executor = SyncInteractorExecutor(),
      getMovie = getMovie,
      movieDetailModelFactory = movieDetailModelFactory
    )
    presenter.setView(view)
  }

  @Test
  fun `should display movie values`() {
    val movie = mock<Movie>()
    val movieDetailModel = mock<MovieDetailModel>()
    given(getMovie.invoke(GetMovie.Request(MOVIE_ID)))
      .willReturn(Either.right(movie))
    given(movieDetailModelFactory.createMovieDetailModel(movie))
      .willReturn(movieDetailModel)

    presenter.viewReady(MOVIE_ID)

    verify(view).displayMovieDetailModel(movieDetailModel)
  }

  @Test
  fun `should show error message when interactor returns an exception`() {
    given(getMovie.invoke(GetMovie.Request(MOVIE_ID)))
      .willReturn(Either.left(Exception("Fake error")))

    presenter.viewReady(MOVIE_ID)

    verify(view).showErrorMessage("Fake error")
  }

  @Test
  fun `should go to back when navUp is selected`() {
    presenter.navUpSelected()

    verify(view).goToBack()
  }
}
