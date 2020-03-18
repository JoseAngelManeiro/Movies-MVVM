package com.joseangelmaneiro.movies.data

import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.data.entity.mapper.MovieMapper
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSource
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSource
import com.joseangelmaneiro.movies.domain.Movie
import com.joseangelmaneiro.movies.domain.MoviesRepository

class MoviesRepositoryImpl(
        private val localDataSource: MoviesLocalDataSource,
        private val remoteDataSource: MoviesRemoteDataSource,
        private val movieMapper: MovieMapper): MoviesRepository {

    override fun getMovies(onlyOnline: Boolean): List<Movie> {
        var movieEntityList: List<MovieEntity>
        if (onlyOnline) {
            movieEntityList = remoteDataSource.getMovies()
            saveData(movieEntityList)
        } else {
            movieEntityList = localDataSource.getMovies()
            if (movieEntityList.isEmpty()) {
                movieEntityList = remoteDataSource.getMovies()
                saveData(movieEntityList)
            }
        }
        return movieMapper.transform(movieEntityList)
    }

    private fun saveData(movieEntityList: List<MovieEntity>) {
        localDataSource.deleteAllMovies()
        localDataSource.saveMovies(movieEntityList)
    }

    override fun getMovie(movieId: Int): Movie {
        return movieMapper.transform(localDataSource.getMovie(movieId))!!
    }

}