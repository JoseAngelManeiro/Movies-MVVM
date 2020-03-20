package com.joseangelmaneiro.movies.presentation.presenters

import com.joseangelmaneiro.movies.domain.Either
import com.joseangelmaneiro.movies.domain.interactor.GetMovies
import com.joseangelmaneiro.movies.domain.model.Movie
import com.joseangelmaneiro.movies.platform.executor.SyncInteractorExecutor
import com.joseangelmaneiro.movies.presentation.MovieListView
import com.joseangelmaneiro.movies.presentation.MovieModelFactory
import com.joseangelmaneiro.movies.presentation.model.MovieModel
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MovieListPresenterTest {

  @Mock
  lateinit var getMovies: GetMovies
  @Mock
  lateinit var view: MovieListView
  @Mock
  lateinit var movieModelFactory: MovieModelFactory

  private lateinit var presenter: MovieListPresenter

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    presenter = MovieListPresenter(
      executor = SyncInteractorExecutor(),
      getMovies = getMovies,
      movieModelFactory = movieModelFactory
    )
    presenter.setView(view)
  }

  @Test
  fun `should invokes interactor without refresh mode when view is ready`() {
    val isOnlyOnline = false
    given(getMovies.invoke(GetMovies.Request(isOnlyOnline)))
      .willReturn(Either.right(listOf(mock())))
    given(movieModelFactory.createMovieModels(any()))
      .willReturn(any())

    presenter.viewReady()

    verify(getMovies).invoke(GetMovies.Request(false))
  }

  @Test
  fun `should invokes interactor with refresh mode when refresh is called`() {
    val isOnlyOnline = true
    given(getMovies.invoke(GetMovies.Request(isOnlyOnline)))
      .willReturn(Either.right(listOf(mock())))
    given(movieModelFactory.createMovieModels(any()))
      .willReturn(any())

    presenter.refresh()

    verify(getMovies).invoke(GetMovies.Request(true))
  }

  @Test
  fun `should shows movies when interactor returns correct data`() {
    val movies = listOf<Movie>(mock())
    val movieModels = listOf<MovieModel>(mock())
    given(getMovies.invoke(any()))
      .willReturn(Either.right(movies))
    given(movieModelFactory.createMovieModels(movies))
      .willReturn(movieModels)

    presenter.viewReady()

    verify(view).cancelRefreshDialog()
    verify(view).showMovies(movieModels)
  }

  @Test
  fun `should show error message when interactor returns an exception`() {
    given(getMovies.invoke(any()))
      .willReturn(Either.left(Exception("Fake error")))

    presenter.viewReady()

    verify(view).cancelRefreshDialog()
    verify(view).showErrorMessage("Fake error")
  }
}
