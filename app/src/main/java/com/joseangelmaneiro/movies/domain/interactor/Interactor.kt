package com.joseangelmaneiro.movies.domain.interactor

import com.joseangelmaneiro.movies.domain.Either

interface Interactor<Request, Response> {

  operator fun invoke(request: Request): Either<Exception, Response>
}
