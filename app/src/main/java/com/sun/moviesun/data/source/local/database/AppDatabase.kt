package com.sun.moviesun.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.data.source.local.database.AppDatabase.Companion.DB_VERSION
import com.sun.moviesun.data.source.local.database.dao.MovieDao

@Database(entities = [(Movie::class)], version = DB_VERSION, exportSchema = false)
@TypeConverters(value = [Converters::class])
abstract class AppDatabase : RoomDatabase() {

  abstract fun movieDao(): MovieDao

  companion object {
    const val DB_VERSION = 1
    const val DB_NAME = "SunMovie.db"
    private var sInstance: AppDatabase? = null
    private val sLock = Any()

    @JvmStatic
    fun getInstance(context: Context): AppDatabase {
      if (sInstance == null) {
        synchronized(sLock) {
          sInstance = Room
              .databaseBuilder(context, AppDatabase::class.java, DB_NAME)
              .allowMainThreadQueries()
              .build()
        }
      }
      return sInstance!!
    }

    fun clearInstance() { sInstance = null }
  }
}
