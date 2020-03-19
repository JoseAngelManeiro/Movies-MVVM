package com.joseangelmaneiro.movies.platform.executor

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class BackgroundRunner(
  private val executorService: Executor = Executors.newFixedThreadPool(3)
) : Runner {

  override fun invoke(c: () -> Unit) {
    executorService.execute(c)
  }
}
