package com.joseangelmaneiro.movies.platform

import android.app.Activity
import com.joseangelmaneiro.movies.platform.views.DetailMovieActivity

fun Activity.navigateToDetail(movieId: Int) {
  DetailMovieActivity.launch(this, movieId)
}