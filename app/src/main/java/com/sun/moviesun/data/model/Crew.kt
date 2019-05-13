package com.sun.moviesun.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Crew (
    val id: Int,
    val name: String?,
    val gender: Int,
    val job: String?,
    val department: String?,
    @SerializedName("credit_id")
    val creditId: String?,
    @SerializedName("profile_path")
    val profilePath: String?
) : Parcelable {
  constructor(source: Parcel) : this(
      source.readInt(),
      source.readString(),
      source.readInt(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeInt(id)
    writeString(name)
    writeInt(gender)
    writeString(job)
    writeString(department)
    writeString(creditId)
    writeString(profilePath)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<Crew> = object : Parcelable.Creator<Crew> {
      override fun createFromParcel(source: Parcel): Crew = Crew(source)
      override fun newArray(size: Int): Array<Crew?> = arrayOfNulls(size)
    }
  }
}