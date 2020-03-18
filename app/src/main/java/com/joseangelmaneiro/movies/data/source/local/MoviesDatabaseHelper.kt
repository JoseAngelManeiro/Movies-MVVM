package com.joseangelmaneiro.movies.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Database Info
private const val DATABASE_NAME = "moviesDB"
private const val DATABASE_VERSION = 1

class MoviesDatabaseHelper(
  context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

  // Called when the database is created for the FIRST time.
  override fun onCreate(db: SQLiteDatabase) {
    db.execSQL(
      "CREATE TABLE " + DbContract.TABLE_NAME + "(" +
        DbContract.COLUMN_ID + " INTEGER PRIMARY KEY," +
        DbContract.COLUMN_VOTE_COUNT + " INTEGER," +
        DbContract.COLUMN_VIDEO + " INTEGER," +
        DbContract.COLUMN_VOTE_AVERAGE + " TEXT," +
        DbContract.COLUMN_TITLE + " TEXT," +
        DbContract.COLUMN_POPULARITY + " REAL," +
        DbContract.COLUMN_POSTERPATH + " TEXT," +
        DbContract.COLUMN_ORIGINAL_LANGUAGE + " TEXT," +
        DbContract.COLUMN_ORIGINAL_TITLE + " TEXT," +
        DbContract.COLUMN_GENRE_IDS + " TEXT," +
        DbContract.COLUMN_BACKDROPPATH + " TEXT," +
        DbContract.COLUMN_ADULT + " INTEGER, " +
        DbContract.COLUMN_OVERVIEW + " TEXT," +
        DbContract.COLUMN_RELEASEDATE + " TEXT" + ")"
    )
  }

  // Called when the database needs to be upgraded.
  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    // Simplest implementation is to drop all old tables and recreate them
    db.execSQL("DROP TABLE IF EXISTS $DbContract.TABLE_NAME")
    onCreate(db)
  }
}