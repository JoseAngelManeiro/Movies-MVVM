package com.joseangelmaneiro.movies.platform.features.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joseangelmaneiro.movies.domain.interactor.GetMovie
import com.joseangelmaneiro.movies.platform.Resource
import com.joseangelmaneiro.movies.platform.executor.InteractorExecutor

class DetailMovieViewModel(
  private val executor: InteractorExecutor,
  private val getMovie: GetMovie,
  private val movieDetailModelFactory: MovieDetailModelFactory
) : ViewModel() {

  private val _movieDetailModel = MutableLiveData<Resource<MovieDetailModel>>()

  val movieDetailModel: LiveData<Resource<MovieDetailModel>>
    get() = _movieDetailModel

  fun load(movieId: Int) {
    _movieDetailModel.value = Resource.loading()
    executor(
      interactor = getMovie,
      request = GetMovie.Request(movieId),
      onError = {
        _movieDetailModel.value = Resource.error(it)
      },
      onSuccess = {
        _movieDetailModel.value =
          Resource.success(movieDetailModelFactory.createMovieDetailModel(it))
      }
    )
  }
}