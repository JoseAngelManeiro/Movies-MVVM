package com.joseangelmaneiro.movies.data.source.local

import android.text.TextUtils

class DBUtils {

    companion object {
        private const val SEPARATOR = ","

        fun transformIntegerListToString(integerList: List<Int>?): String? {
            return if (integerList != null) {
                TextUtils.join(SEPARATOR, integerList)
            } else {
                null
            }
        }

        fun transformStringToIntegerList(text: String): List<Int> {
            val arrayDB = text.split(SEPARATOR)
            val integerList = mutableListOf<Int>()
            for (s in arrayDB) {
                integerList.add(Integer.parseInt(s))
            }
            return integerList
        }
    }
}