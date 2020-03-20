package com.joseangelmaneiro.movies.presentation.model

import java.util.*

data class MovieDetailModel(
  val voteAverage: String,
  val title: String,
  val backdropPath: String?,
  val overview: String,
  val releaseDate: Date
)