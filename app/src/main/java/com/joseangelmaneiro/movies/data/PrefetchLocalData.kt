package com.joseangelmaneiro.movies.data

import com.joseangelmaneiro.movies.domain.Either

abstract class PrefetchLocalData<RequestType, ResultType> {

  fun load(): Either<Exception, ResultType> {
    val localData = loadFromLocal()
    return if (shouldFetch(localData)) {
      val serviceData = loadFromService()
      if (serviceData.isRight) {
        saveServiceResult(serviceData.rightValue)
        Either.Right(loadFromLocal()!!)
      } else {
        Either.Left(serviceData.leftValue)
      }
    } else {
      Either.Right(localData!!)
    }
  }

  abstract fun loadFromLocal(): ResultType?

  abstract fun shouldFetch(data: ResultType?): Boolean

  abstract fun loadFromService(): Either<Exception, RequestType>

  abstract fun saveServiceResult(item: RequestType)
}
