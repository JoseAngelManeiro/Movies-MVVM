package com.joseangelmaneiro.movies.platform

import com.joseangelmaneiro.movies.platform.Status.SUCCESS
import com.joseangelmaneiro.movies.platform.Status.ERROR
import com.joseangelmaneiro.movies.platform.Status.LOADING

data class Resource<out T>(
  val status: Status,
  val data: T?,
  val exception: Exception?
) {
  companion object {
    fun <T> success(data: T?): Resource<T> {
      return Resource(SUCCESS, data, null)
    }

    fun <T> error(exception: Exception): Resource<T> {
      return Resource(ERROR, null, exception)
    }

    fun <T> loading(): Resource<T> {
      return Resource(LOADING, null, null)
    }
  }
}
