package com.sun.moviesun.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MovieDetail(
    val id: Int,
    val homepage: String?,
    val genres: List<Genre>,
    @SerializedName("production_companies")
    val productionCompanies: List<Company>?
) : Parcelable {

  constructor(source: Parcel) : this(
      source.readInt(),
      source.readString(),
      ArrayList<Genre>().apply { source.readList(this, Genre::class.java.classLoader) },
      ArrayList<Company>().apply { source.readList(this, Company::class.java.classLoader) }
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeInt(id)
    writeString(homepage)
    writeList(genres)
    writeList(productionCompanies)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<MovieDetail> = object : Parcelable.Creator<MovieDetail> {
      override fun createFromParcel(source: Parcel): MovieDetail = MovieDetail(source)
      override fun newArray(size: Int): Array<MovieDetail?> = arrayOfNulls(size)
    }
  }
}
