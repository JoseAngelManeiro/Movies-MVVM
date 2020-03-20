package com.joseangelmaneiro.movies.presentation.presenters

import com.joseangelmaneiro.movies.TestUtils
import com.joseangelmaneiro.movies.domain.Either
import com.joseangelmaneiro.movies.domain.interactor.GetMovie
import com.joseangelmaneiro.movies.platform.executor.SyncInteractorExecutor
import com.joseangelmaneiro.movies.presentation.DetailMovieView
import com.joseangelmaneiro.movies.presentation.formatters.Formatter
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val MOVIE_ID = 1234
private const val RELEASE_DATE = "22/10/2017"
private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500fake_poster_path.png"

class DetailMoviePresenterTest {

  @Mock
  lateinit var getMovie: GetMovie
  @Mock
  lateinit var formatter: Formatter
  @Mock
  lateinit var view: DetailMovieView

  private lateinit var presenter: DetailMoviePresenter

  @Before
  @Throws(Exception::class)
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    presenter = DetailMoviePresenter(
      executor = SyncInteractorExecutor(),
      getMovie = getMovie,
      formatter = formatter
    )
    presenter.setView(view)
  }

  @Test
  fun `should display movie values`() {
    val movie = TestUtils.createMovie()
    whenever(formatter.getCompleteUrlImage(movie.backdropPath)).thenReturn(IMAGE_URL)
    whenever(formatter.formatDate(movie.releaseDate)).thenReturn(RELEASE_DATE)
    whenever(getMovie.invoke(GetMovie.Request(MOVIE_ID))).thenReturn(Either.right(movie))

    presenter.viewReady(MOVIE_ID)

    verify(view).displayImage(IMAGE_URL)
    verify(view).displayTitle(movie.title)
    verify(view).displayVoteAverage(movie.voteAverage)
    verify(view).displayReleaseDate(RELEASE_DATE)
    verify(view).displayOverview(movie.overview)
  }

  @Test
  fun `should go to back when navUp is selected`() {
    presenter.navUpSelected()

    verify(view).goToBack()
  }
}
