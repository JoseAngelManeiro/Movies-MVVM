package com.joseangelmaneiro.movies.data.source.local

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.joseangelmaneiro.movies.data.entity.MovieEntity
import com.joseangelmaneiro.movies.data.source.local.DbContract.COLUMN_ID
import com.joseangelmaneiro.movies.data.source.local.DbContract.TABLE_NAME
import java.util.ArrayList

class MoviesLocalDataSourceImpl(
  private val sqLiteOpenHelper: SQLiteOpenHelper
) : MoviesLocalDataSource {

  private val db: SQLiteDatabase by lazy { sqLiteOpenHelper.readableDatabase }

  override fun getMovies(): List<MovieEntity> {
    val sql = "SELECT * FROM $TABLE_NAME"
    val movieEntityList = ArrayList<MovieEntity>()

    val cursor = db.rawQuery(sql, null)
    if (cursor.moveToFirst()) {
      do {
        movieEntityList.add(readMovieEntity(cursor))
      } while (cursor.moveToNext())
    }

    if (!cursor.isClosed) {
      cursor.close()
    }

    return movieEntityList
  }

  override fun getMovie(movieId: Int): MovieEntity? {
    val sql = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $movieId"
    var movieEntity: MovieEntity? = null

    val cursor = db.rawQuery(sql, null)
    if (cursor.moveToFirst()) {
      movieEntity = readMovieEntity(cursor)
    }

    if (!cursor.isClosed) {
      cursor.close()
    }

    return movieEntity
  }

  override fun saveMovies(movieEntityList: List<MovieEntity>) {
    // It's a good idea to wrap our insert in a transaction.
    // This helps with performance and ensures consistency of the database.
    db.beginTransaction()
    for (movieEntity in movieEntityList) {
      db.insert(TABLE_NAME, null, getContentValues(movieEntity))
    }
    db.setTransactionSuccessful()
    db.endTransaction()
  }

  override fun deleteAllMovies() {
    db.delete(TABLE_NAME, null, null)
  }

  private fun readMovieEntity(cursor: Cursor) =
    MovieEntity(
      cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
      cursor.getInt(cursor.getColumnIndex(DbContract.COLUMN_VOTE_COUNT)),
      cursor.getInt(cursor.getColumnIndex(DbContract.COLUMN_VIDEO)) == 1,
      cursor.getString(cursor.getColumnIndex(DbContract.COLUMN_VOTE_AVERAGE)),
      cursor.getString(cursor.getColumnIndex(DbContract.COLUMN_TITLE)),
      cursor.getFloat(cursor.getColumnIndex(DbContract.COLUMN_POPULARITY)),
      cursor.getString(cursor.getColumnIndex(DbContract.COLUMN_POSTERPATH)),
      cursor.getString(cursor.getColumnIndex(DbContract.COLUMN_ORIGINAL_LANGUAGE)),
      cursor.getString(cursor.getColumnIndex(DbContract.COLUMN_ORIGINAL_TITLE)),
      DBUtils.transformStringToIntegerList(cursor.getString(cursor.getColumnIndex(DbContract.COLUMN_GENRE_IDS))),
      cursor.getString(cursor.getColumnIndex(DbContract.COLUMN_BACKDROPPATH)),
      cursor.getInt(cursor.getColumnIndex(DbContract.COLUMN_ADULT)) == 1,
      cursor.getString(cursor.getColumnIndex(DbContract.COLUMN_OVERVIEW)),
      cursor.getString(cursor.getColumnIndex(DbContract.COLUMN_RELEASEDATE))
    )

  private fun getContentValues(movieEntity: MovieEntity): ContentValues {
    val values = ContentValues()
    values.put(COLUMN_ID, movieEntity.id)
    values.put(DbContract.COLUMN_VOTE_COUNT, movieEntity.voteCount)
    values.put(DbContract.COLUMN_VIDEO, if (movieEntity.video) 1 else 0)
    values.put(DbContract.COLUMN_VOTE_AVERAGE, movieEntity.voteAverage)
    values.put(DbContract.COLUMN_TITLE, movieEntity.title)
    values.put(DbContract.COLUMN_POPULARITY, movieEntity.popularity)
    values.put(DbContract.COLUMN_POSTERPATH, movieEntity.posterPath)
    values.put(DbContract.COLUMN_ORIGINAL_LANGUAGE, movieEntity.originalLanguage)
    values.put(DbContract.COLUMN_ORIGINAL_TITLE, movieEntity.originalTitle)
    values.put(
      DbContract.COLUMN_GENRE_IDS,
      DBUtils.transformIntegerListToString(movieEntity.genreIds)
    )
    values.put(DbContract.COLUMN_BACKDROPPATH, movieEntity.backdropPath)
    values.put(DbContract.COLUMN_ADULT, if (movieEntity.adult) 1 else 0)
    values.put(DbContract.COLUMN_OVERVIEW, movieEntity.overview)
    values.put(DbContract.COLUMN_RELEASEDATE, movieEntity.releaseDate)
    return values
  }
}
