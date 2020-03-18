package com.joseangelmaneiro.movies.data.exception

class ServiceException(errorMessage: String = "An error has occurred with the server") :
  Exception(errorMessage)