package com.joseangelmaneiro.movies.platform.features.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joseangelmaneiro.movies.domain.interactor.GetMovies
import com.joseangelmaneiro.movies.platform.Resource
import com.joseangelmaneiro.movies.platform.executor.InteractorExecutor

class MovieListViewModel(
  private val executor: InteractorExecutor,
  private val getMovies: GetMovies,
  private val movieModelFactory: MovieModelFactory
) : ViewModel() {

  private val _movieModels = MutableLiveData<Resource<List<MovieModel>>>()

  val movieModels: LiveData<Resource<List<MovieModel>>>
    get() = _movieModels

  fun load() {
    invokeGetMovies(false)
  }

  fun refresh() {
    invokeGetMovies(true)
  }

  private fun invokeGetMovies(refresh: Boolean) {
    _movieModels.value = Resource.loading()
    executor(
      interactor = getMovies,
      request = GetMovies.Request(refresh),
      onError = {
        _movieModels.value = Resource.error(it)
      },
      onSuccess = {
        _movieModels.value =
          Resource.success(movieModelFactory.createMovieModels(it))
      }
    )
  }
}