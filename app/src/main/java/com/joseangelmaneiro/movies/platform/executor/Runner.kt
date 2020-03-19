package com.joseangelmaneiro.movies.platform.executor

interface Runner {
  operator fun invoke(c: () -> Unit)
}
