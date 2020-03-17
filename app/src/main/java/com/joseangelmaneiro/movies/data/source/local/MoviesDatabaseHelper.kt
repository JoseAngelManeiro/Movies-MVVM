package com.joseangelmaneiro.movies.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


// Database Info
private const val DATABASE_NAME = "moviesDB"
private const val DATABASE_VERSION = 1

// Table Name
private const val TABLE_MOVIE = "movie"

// Movie Table Columns
private const val KEY_ID = "id"
private const val KEY_VOTE_COUNT = "voteCount"
private const val KEY_VIDEO = "video"
private const val KEY_VOTE_AVERAGE = "voteAverage"
private const val KEY_TITLE = "title"
private const val KEY_POPULARITY = "popularity"
private const val KEY_POSTERPATH = "posterpath"
private const val KEY_ORIGINAL_LANGUAGE = "originalLanguage"
private const val KEY_ORIGINAL_TITLE = "originalTitle"
private const val KEY_GENRE_IDS = "genreIds"
private const val KEY_BACKDROPPATH = "backdroppath"
private const val KEY_ADULT = "adult"
private const val KEY_OVERVIEW = "overview"
private const val KEY_RELEASEDATE = "releasedate"


class MoviesDatabaseHelper(context: Context):
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Called when the database is created for the FIRST time.
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_MOVIE +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_VOTE_COUNT + " INTEGER," +
                KEY_VIDEO + " INTEGER," +
                KEY_VOTE_AVERAGE + " TEXT," +
                KEY_TITLE + " TEXT," +
                KEY_POPULARITY + " REAL," +
                KEY_POSTERPATH + " TEXT," +
                KEY_ORIGINAL_LANGUAGE + " TEXT," +
                KEY_ORIGINAL_TITLE + " TEXT," +
                KEY_GENRE_IDS + " TEXT," +
                KEY_BACKDROPPATH + " TEXT," +
                KEY_ADULT + " INTEGER, " +
                KEY_OVERVIEW + " TEXT," +
                KEY_RELEASEDATE + " TEXT" +
                ")"

        db.execSQL(CREATE_MOVIE_TABLE)
    }

    // Called when the database needs to be upgraded.
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Simplest implementation is to drop all old tables and recreate them
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIE")
            onCreate(db)
        }
    }

}