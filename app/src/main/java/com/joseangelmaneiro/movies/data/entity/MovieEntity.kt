package com.joseangelmaneiro.movies.data.entity

import com.google.gson.annotations.SerializedName

data class MovieEntity(
  @SerializedName("id") val id: Int,
  @SerializedName("vote_count") val voteCount: Int,
  @SerializedName("video") val video: Boolean,
  @SerializedName("vote_average") val voteAverage: String,
  @SerializedName("title") val title: String,
  @SerializedName("popularity") val popularity: Float,
  @SerializedName("poster_path") val posterPath: String,
  @SerializedName("original_language") val originalLanguage: String,
  @SerializedName("original_title") val originalTitle: String,
  @SerializedName("genre_ids") val genreIds: List<Int>?,
  @SerializedName("backdrop_path") val backdropPath: String?,
  @SerializedName("adult") val adult: Boolean,
  @SerializedName("overview") val overview: String,
  @SerializedName("release_date") val releaseDate: String
)