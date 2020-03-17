package com.joseangelmaneiro.movies.domain.interactor

import org.junit.Assert.*
import org.mockito.MockitoAnnotations
import org.junit.Before
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test


class UseCaseFactoryTest{

    lateinit var sut: UseCaseFactory


    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = UseCaseFactory(mock(), mock(), mock())
    }

    @Test
    fun getMovie_CreatesCorrectInstance() {
        val useCase = sut.getMovie()

        assertTrue(useCase is GetMovie)
    }

    @Test
    fun getMovies_CreatesCorrectInstance() {
        val useCase = sut.getMovies()

        assertTrue(useCase is GetMovies)
    }

}