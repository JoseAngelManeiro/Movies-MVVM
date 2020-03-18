package com.joseangelmaneiro.movies.data.exception

class ElementNotFoundException(errorMessage: String = "Element is not in the database") :
  Exception(errorMessage)