package com.joseangelmaneiro.movies.presentation.formatters

import com.joseangelmaneiro.movies.platform.Formatter
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class FormatterTest {

  private lateinit var sut: Formatter

  @Before
  fun setUp() {
    sut = Formatter()
  }

  @Test
  fun formatDate_ReturnsCorrectDate() {
    val date = GregorianCalendar(2017, Calendar.OCTOBER, 22).time
    val dateExpected = "22/10/2017"

    Assert.assertEquals(dateExpected, sut.formatDate(date))
  }

}