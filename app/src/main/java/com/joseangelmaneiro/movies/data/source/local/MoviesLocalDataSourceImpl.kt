package com.joseangelmaneiro.movies.data.source.local

import android.database.sqlite.SQLiteOpenHelper
import com.joseangelmaneiro.movies.data.entity.MovieEntity
import java.util.ArrayList


class MoviesLocalDataSourceImpl(
        private val sqLiteOpenHelper: SQLiteOpenHelper): MoviesLocalDataSource {

    override fun getMovies(): List<MovieEntity> {
        val db = sqLiteOpenHelper.readableDatabase
        val sql = "SELECT * FROM $TABLE_NAME"
        val movieEntityList = ArrayList<MovieEntity>()

        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                movieEntityList.add(MovieEntity(cursor))
            } while (cursor.moveToNext())
        }

        if (!cursor.isClosed) {
            cursor.close()
        }

        return movieEntityList
    }

    override fun getMovie(movieId: Int): MovieEntity? {
        val db = sqLiteOpenHelper.readableDatabase
        val sql = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $movieId"
        var movieEntity: MovieEntity? = null

        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            movieEntity = MovieEntity(cursor)
        }

        if (!cursor.isClosed) {
            cursor.close()
        }

        return movieEntity
    }

    override fun saveMovies(movieEntityList: List<MovieEntity>) {
        val db = sqLiteOpenHelper.writableDatabase

        // It's a good idea to wrap our insert in a transaction.
        // This helps with performance and ensures consistency of the database.
        db.beginTransaction()
        for (movieEntity in movieEntityList) {
            db.insert(TABLE_NAME, null, movieEntity.getContentValues())
        }
        db.setTransactionSuccessful()
        db.endTransaction()
    }

    override fun deleteAllMovies() {
        val db = sqLiteOpenHelper.writableDatabase
        db.delete(TABLE_NAME, null, null)
    }

}
