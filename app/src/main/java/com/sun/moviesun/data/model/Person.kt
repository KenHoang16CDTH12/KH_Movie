package com.sun.moviesun.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Person(
    val id: Int,
    val name: String?,
    var page: Int,
    val adult: Boolean,
    val popularity: Float,
    @SerializedName("profile_path")
    val profilePath: String?
) : Parcelable {

  constructor(source: Parcel) : this(
      source.readInt(),
      source.readString(),
      source.readInt(),
      1 == source.readInt(),
      source.readFloat(),
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeInt(page)
    writeString(profilePath)
    writeInt((if (adult) 1 else 0))
    writeInt(id)
    writeString(name)
    writeFloat(popularity)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<Person> = object : Parcelable.Creator<Person> {
      override fun createFromParcel(source: Parcel): Person = Person(source)
      override fun newArray(size: Int): Array<Person?> = arrayOfNulls(size)
    }
  }
}