package com.joseangelmaneiro.movies.data.entity

import android.content.ContentValues
import android.database.Cursor
import com.google.gson.annotations.SerializedName
import com.joseangelmaneiro.movies.data.source.local.*

data class MovieEntity(
        @SerializedName("id") val id: Int,
        @SerializedName("vote_count") val voteCount: Int,
        @SerializedName("video") val video: Boolean,
        @SerializedName("vote_average") val voteAverage: String,
        @SerializedName("title") val title: String,
        @SerializedName("popularity") val popularity: Float,
        @SerializedName("poster_path") val posterPath: String,
        @SerializedName("original_language") val originalLanguage: String,
        @SerializedName("original_title") val originalTitle: String,
        @SerializedName("genre_ids") val genreIds: List<Int>?,
        @SerializedName("backdrop_path") val backdropPath: String?,
        @SerializedName("adult") val adult: Boolean,
        @SerializedName("overview") val overview: String,
        @SerializedName("release_date") val releaseDate: String){

    constructor(cursor: Cursor): this(
        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
        cursor.getInt(cursor.getColumnIndex(COLUMN_VOTE_COUNT)),
        cursor.getInt(cursor.getColumnIndex(COLUMN_VIDEO)) == 1,
        cursor.getString(cursor.getColumnIndex(COLUMN_VOTE_AVERAGE)),
        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
        cursor.getFloat(cursor.getColumnIndex(COLUMN_POPULARITY)),
        cursor.getString(cursor.getColumnIndex(COLUMN_POSTERPATH)),
        cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_LANGUAGE)),
        cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_TITLE)),
        DBUtils.transformStringToIntegerList(cursor.getString(cursor.getColumnIndex(COLUMN_GENRE_IDS))),
        cursor.getString(cursor.getColumnIndex(COLUMN_BACKDROPPATH)),
        cursor.getInt(cursor.getColumnIndex(COLUMN_ADULT)) == 1,
        cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW)),
        cursor.getString(cursor.getColumnIndex(COLUMN_RELEASEDATE)))

    fun getContentValues(): ContentValues {
        val values = ContentValues()
        values.put(COLUMN_ID, this.id)
        values.put(COLUMN_VOTE_COUNT, this.voteCount)
        values.put(COLUMN_VIDEO, if (this.video) 1 else 0)
        values.put(COLUMN_VOTE_AVERAGE, this.voteAverage)
        values.put(COLUMN_TITLE, this.title)
        values.put(COLUMN_POPULARITY, this.popularity)
        values.put(COLUMN_POSTERPATH, this.posterPath)
        values.put(COLUMN_ORIGINAL_LANGUAGE, this.originalLanguage)
        values.put(COLUMN_ORIGINAL_TITLE, this.originalTitle)
        values.put(COLUMN_GENRE_IDS, DBUtils.transformIntegerListToString(this.genreIds))
        values.put(COLUMN_BACKDROPPATH, this.backdropPath)
        values.put(COLUMN_ADULT, if (this.adult) 1 else 0)
        values.put(COLUMN_OVERVIEW, this.overview)
        values.put(COLUMN_RELEASEDATE, this.releaseDate)
        return values
    }

}