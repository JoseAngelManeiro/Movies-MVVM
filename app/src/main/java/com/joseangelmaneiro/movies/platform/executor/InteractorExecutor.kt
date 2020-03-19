package com.joseangelmaneiro.movies.platform.executor

import com.joseangelmaneiro.movies.domain.interactor.Interactor

interface InteractorExecutor {

  operator fun <Request, Response> invoke(
    interactor: Interactor<Request, Response>,
    request: Request,
    onError: (Exception) -> Unit = {},
    onSuccess: (Response) -> Unit = {}
  )
}
