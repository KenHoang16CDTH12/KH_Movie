package com.sun.moviesun.data.model.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sun.moviesun.data.model.Genre
import com.sun.moviesun.data.model.Keyword
import com.sun.moviesun.data.model.Review
import com.sun.moviesun.data.model.Video

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    var id: Int,
    var page: Int,
    var title: String?,
    var adult: Boolean,
    var video: Boolean,
    var popularity: Float,
    var overview: String? = "",
    var videos: List<Video>? = ArrayList(),
    var reviews: List<Review>? = ArrayList(),
    var keywords: List<Keyword>? = ArrayList(),
    var genres: List<Genre>,
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("release_date")
    var releaseDate: String? = "",
    @SerializedName("original_title")
    var originalTitle: String? = "",
    @SerializedName("original_language")
    var originalLanguage: String? = "",
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("vote_count")
    var voteCount: Int,
    @SerializedName("vote_average")
    var voteAverage: Float
) : Parcelable {

  constructor(source: Parcel) : this(
      source.readInt(),
      source.readInt(),
      source.readString(),
      1 == source.readInt(),
      1 == source.readInt(),
      source.readFloat(),
      source.readString(),
      ArrayList<Video>().apply { source.readList(this, Video::class.java.classLoader) },
      ArrayList<Review>().apply { source.readList(this, Review::class.java.classLoader) },
      ArrayList<Keyword>().apply { source.readList(this, Keyword::class.java.classLoader) },
      ArrayList<Genre>().apply { source.readList(this, Genre::class.java.classLoader) },
      source.readString(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readInt(),
      source.readFloat()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeInt(id)
    writeInt(page)
    writeString(title)
    writeByte(if (adult) 1 else 0)
    writeByte(if (video) 1 else 0)
    writeFloat(popularity)
    writeString(overview)
    writeList(videos)
    writeList(reviews)
    writeList(keywords)
    writeList(genres)
    writeString(posterPath)
    writeString(releaseDate)
    writeString(originalTitle)
    writeString(originalLanguage)
    writeString(backdropPath)
    writeInt(voteCount)
    writeFloat(voteAverage)
  }

  fun voteAverageSeparate() : Float =
      voteAverage / 2 // Because app should show Rating Bar max = 5, so need to separate the average vote

  companion object {

    @JvmField
    val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
      override fun createFromParcel(source: Parcel): Movie = Movie(source)
      override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
    }
  }
}
