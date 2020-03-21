package com.joseangelmaneiro.movies.presentation.presenters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.joseangelmaneiro.movies.domain.Either
import com.joseangelmaneiro.movies.domain.interactor.GetMovie
import com.joseangelmaneiro.movies.domain.model.Movie
import com.joseangelmaneiro.movies.platform.Resource
import com.joseangelmaneiro.movies.platform.executor.SyncInteractorExecutor
import com.joseangelmaneiro.movies.platform.features.detail.DetailMovieViewModel
import com.joseangelmaneiro.movies.platform.features.detail.MovieDetailModelFactory
import com.joseangelmaneiro.movies.platform.features.detail.MovieDetailModel
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

private const val MOVIE_ID = 1234

class DetailMovieViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val getMovie = mock<GetMovie>()
  private val movieDetailModelFactory = mock<MovieDetailModelFactory>()

  private val observer = mock<Observer<Resource<MovieDetailModel>>>()

  private lateinit var viewModel: DetailMovieViewModel

  @Before
  fun setUp() {
    viewModel = DetailMovieViewModel(
      executor = SyncInteractorExecutor(),
      getMovie = getMovie,
      movieDetailModelFactory = movieDetailModelFactory
    )
    viewModel.movieDetailModel.observeForever(observer)
  }

  @Test
  fun `should display movie values`() {
    val movie = mock<Movie>()
    val movieDetailModel = mock<MovieDetailModel>()
    given(getMovie.invoke(GetMovie.Request(MOVIE_ID)))
      .willReturn(Either.right(movie))
    given(movieDetailModelFactory.createMovieDetailModel(movie))
      .willReturn(movieDetailModel)

    viewModel.load(MOVIE_ID)

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(Resource.success(movieDetailModel))
  }

  @Test
  fun `should show error message when interactor returns an exception`() {
    val exception = Exception("Fake error")
    given(getMovie.invoke(GetMovie.Request(MOVIE_ID)))
      .willReturn(Either.left(exception))

    viewModel.load(MOVIE_ID)

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(Resource.error(exception))
  }
}
