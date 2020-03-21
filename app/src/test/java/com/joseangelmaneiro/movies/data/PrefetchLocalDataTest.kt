package com.joseangelmaneiro.movies.data

import com.joseangelmaneiro.movies.domain.Either
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify

class PrefetchLocalDataTest {

  private val handleLoadFromLocal = mock<() -> Foo?>()
  private val handleShouldFetch = mock<(Foo?) -> Boolean>()
  private val handleLoadFromService = mock<() -> Either<Exception, Foo>>()
  private val handleSaveServiceResult = mock<(Foo) -> Unit>()

  private lateinit var prefetchLocalData: PrefetchLocalData<Foo, Foo>

  @Before
  fun setUp() {
    prefetchLocalData = object : PrefetchLocalData<Foo, Foo>() {
      override fun loadFromLocal(): Foo? {
        return handleLoadFromLocal()
      }

      override fun shouldFetch(data: Foo?): Boolean {
        return handleShouldFetch(data)
      }

      override fun loadFromService(): Either<Exception, Foo> {
        return handleLoadFromService()
      }

      override fun saveServiceResult(item: Foo) {
        handleSaveServiceResult(item)
      }
    }
  }

  @Test
  fun `local data is always returned first if the data should not be fetched`() {
    val localData = Foo(1)
    given(handleLoadFromLocal()).willReturn(localData)
    given(handleShouldFetch(localData)).willReturn(false)

    val result = prefetchLocalData.load()

    assertTrue(result.isRight)
    assertEquals(localData, result.rightValue)
  }

  @Test
  fun `data will be obtained from the service where applicable`() {
    val localDataFirstTime = null
    val localDataAfterSaving = Foo(1)
    val serviceData = Foo(2)
    given(handleLoadFromLocal())
      .willReturn(localDataFirstTime)
      .willReturn(localDataAfterSaving)
    given(handleShouldFetch(localDataFirstTime)).willReturn(true)
    given(handleLoadFromService()).willReturn(Either.Right(serviceData))

    val result = prefetchLocalData.load()

    assertTrue(result.isRight)
    verify(handleSaveServiceResult).invoke(serviceData)
    assertEquals(localDataAfterSaving, result.rightValue)
  }

  @Test
  fun `if there is no valid local data and the service fails, an exception is returned`() {
    val localData = null
    val serviceException = Exception("Any exception")
    given(handleLoadFromLocal()).willReturn(localData)
    given(handleShouldFetch(localData)).willReturn(true)
    given(handleLoadFromService()).willReturn(Either.Left(serviceException))

    val result = prefetchLocalData.load()

    assertTrue(result.isLeft)
    assertEquals(serviceException, result.leftValue)
  }

  private data class Foo(val value: Int)
}
