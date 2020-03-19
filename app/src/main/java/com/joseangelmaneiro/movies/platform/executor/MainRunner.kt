package com.joseangelmaneiro.movies.platform.executor

import android.os.Handler
import android.os.Looper

class MainRunner(
  private val handler: Handler = Handler(Looper.getMainLooper())
) : Runner {

  override fun invoke(c: () -> Unit) {
    handler.post(c)
  }
}
