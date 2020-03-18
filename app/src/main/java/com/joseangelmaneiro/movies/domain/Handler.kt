package com.joseangelmaneiro.movies.domain

//Callback to comunicate between layers
interface Handler<T> {

  fun handle(result: T)

  fun error(exception: Exception)

}