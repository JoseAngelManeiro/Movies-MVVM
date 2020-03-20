package com.joseangelmaneiro.movies.presentation.formatters

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Formatter {

  companion object {

    val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500"

    private val SERVER_DATE_FORMAT = "yyyy-MM-dd"
    private val APP_DATE_FORMAT = "dd/MM/yyyy"
  }

  fun getCompleteUrlImage(posterPath: String?): String {
    return BASE_URL_IMAGE + posterPath
  }

  fun formatDate(serverDate: String): String {
    lateinit var date: Date
    try {
      val serverDateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.getDefault())
      date = serverDateFormat.parse(serverDate)
    } catch (ignored: ParseException) {
    }

    val appDateFormat = SimpleDateFormat(APP_DATE_FORMAT, Locale.getDefault())
    return appDateFormat.format(date)
  }
}