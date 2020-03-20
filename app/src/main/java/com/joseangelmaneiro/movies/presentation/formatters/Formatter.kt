package com.joseangelmaneiro.movies.presentation.formatters

import java.text.SimpleDateFormat
import java.util.*

private const val APP_DATE_FORMAT = "dd/MM/yyyy"

class Formatter {

  fun formatDate(date: Date): String {
    val appDateFormat = SimpleDateFormat(APP_DATE_FORMAT, Locale.US)
    return appDateFormat.format(date)
  }
}