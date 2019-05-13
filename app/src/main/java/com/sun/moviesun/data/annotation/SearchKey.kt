package com.sun.moviesun.data.annotation

import androidx.annotation.StringDef
import com.sun.moviesun.data.annotation.SearchKeyDef.MOVIE
import com.sun.moviesun.data.annotation.SearchKeyDef.COLLECTION
import com.sun.moviesun.data.annotation.SearchKeyDef.COMPANY
import com.sun.moviesun.data.annotation.SearchKeyDef.KEYWORD
import com.sun.moviesun.data.annotation.SearchKeyDef.MULTI
import com.sun.moviesun.data.annotation.SearchKeyDef.PEOPLE
import com.sun.moviesun.data.annotation.SearchKeyDef.TV_SHOW

object SearchKeyDef {
  const val MOVIE = "movie"
  const val TV_SHOW = "tv"
  const val PEOPLE = "person"
  const val COLLECTION = "collection"
  const val COMPANY = "company"
  const val KEYWORD = "keyword"
  const val MULTI = "multi"
}

@Retention(AnnotationRetention.SOURCE)
@StringDef(MOVIE, TV_SHOW, PEOPLE, COLLECTION, COMPANY, KEYWORD, MULTI)
annotation class SearchKey