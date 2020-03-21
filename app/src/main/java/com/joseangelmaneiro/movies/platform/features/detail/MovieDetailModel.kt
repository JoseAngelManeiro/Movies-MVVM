package com.joseangelmaneiro.movies.platform.features.detail

import java.util.*

data class MovieDetailModel(
  val voteAverage: String,
  val title: String,
  val backdropPath: String?,
  val overview: String,
  val releaseDate: Date
)