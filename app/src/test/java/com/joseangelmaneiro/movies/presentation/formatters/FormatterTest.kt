package com.joseangelmaneiro.movies.presentation.formatters

import org.junit.Assert
import org.junit.Before
import org.junit.Test


class FormatterTest {

  private lateinit var sut: Formatter

  @Before
  fun setUp() {
    sut = Formatter()
  }

  @Test
  fun getCompleteUrlImage_ReturnsCorrectUrl() {
    val fakePath = "fake-path"
    val urlExpected = Formatter.BASE_URL_IMAGE + fakePath

    Assert.assertEquals(urlExpected, sut.getCompleteUrlImage(fakePath))
  }

  @Test
  fun formatDate_ReturnsCorrectDate() {
    val serverDate = "2017-10-22"
    val dateExpected = "22/10/2017"

    Assert.assertEquals(dateExpected, sut.formatDate(serverDate))
  }

}