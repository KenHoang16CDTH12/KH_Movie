package com.sun.moviesun.data.annotation

import androidx.annotation.IntDef
import com.sun.moviesun.data.annotation.PageKeyDef.ACTOR
import com.sun.moviesun.data.annotation.PageKeyDef.DISCOVER
import com.sun.moviesun.data.annotation.PageKeyDef.FAVORITE
import com.sun.moviesun.data.annotation.PageKeyDef.GENRE

object PageKeyDef {
  const val DISCOVER = 0
  const val GENRE = 1
  const val FAVORITE = 2
  const val ACTOR = 3
}

@Retention(AnnotationRetention.SOURCE)
@IntDef(DISCOVER, GENRE, FAVORITE, ACTOR)
annotation class PageKey
