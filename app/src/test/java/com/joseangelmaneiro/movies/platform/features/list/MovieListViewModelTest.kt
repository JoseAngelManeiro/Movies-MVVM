package com.joseangelmaneiro.movies.platform.features.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.joseangelmaneiro.movies.domain.Either
import com.joseangelmaneiro.movies.domain.interactor.GetMovies
import com.joseangelmaneiro.movies.domain.model.Movie
import com.joseangelmaneiro.movies.platform.Resource
import com.joseangelmaneiro.movies.platform.executor.SyncInteractorExecutor
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MovieListViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val getMovies = mock<GetMovies>()
  private val  movieModelFactory = mock<MovieModelFactory>()

  private val observer = mock<Observer<Resource<List<MovieModel>>>>()

  private lateinit var viewModel: MovieListViewModel

  @Before
  fun setUp() {
    viewModel = MovieListViewModel(
      executor = SyncInteractorExecutor(),
      getMovies = getMovies,
      movieModelFactory = movieModelFactory
    )
    viewModel.movieModels.observeForever(observer)
  }

  @Test
  fun `should invokes interactor without refresh mode when view is ready`() {
    val isOnlyOnline = false
    given(getMovies.invoke(GetMovies.Request(isOnlyOnline)))
      .willReturn(Either.right(listOf(mock())))
    given(movieModelFactory.createMovieModels(any())).willReturn(any())

    viewModel.load()

    verify(getMovies).invoke(GetMovies.Request(false))
  }

  @Test
  fun `should invokes interactor with refresh mode when refresh is called`() {
    val isOnlyOnline = true
    given(getMovies.invoke(GetMovies.Request(isOnlyOnline)))
      .willReturn(Either.right(listOf(mock())))
    given(movieModelFactory.createMovieModels(any())).willReturn(any())

    viewModel.refresh()

    verify(getMovies).invoke(GetMovies.Request(true))
  }

  @Test
  fun `should shows movies when interactor returns correct data`() {
    val movies = listOf<Movie>(mock())
    val movieModels = listOf<MovieModel>(mock())
    given(getMovies.invoke(any())).willReturn(Either.right(movies))
    given(movieModelFactory.createMovieModels(movies)).willReturn(movieModels)

    viewModel.load()

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(Resource.success(movieModels))
  }

  @Test
  fun `should show error message when interactor returns an exception`() {
    val exception = Exception("Fake error")
    given(getMovies.invoke(any())).willReturn(Either.left(exception))

    viewModel.load()

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(Resource.error(exception))
  }
}