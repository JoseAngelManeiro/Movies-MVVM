package com.joseangelmaneiro.movies.data.exception


class NetworkConnectionException(errorMessage: String = "The connection has failed"):
        Exception(errorMessage)