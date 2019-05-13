package com.sun.moviesun.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Cast (
    val id: Int,
    val name: String?,
    val character: String?,
    val gender: Int,
    val order: Int,
    @SerializedName("cast_id")
    val castId: Int,
    @SerializedName("credit_id")
    val creditId: String?,
    @SerializedName("profile_path")
    val profilePath: String?
) : Parcelable {
  constructor(source: Parcel) : this(
      source.readInt(),
      source.readString(),
      source.readString(),
      source.readInt(),
      source.readInt(),
      source.readInt(),
      source.readString(),
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeInt(id)
    writeString(name)
    writeString(character)
    writeInt(gender)
    writeInt(order)
    writeInt(castId)
    writeString(creditId)
    writeString(profilePath)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<Cast> = object : Parcelable.Creator<Cast> {
      override fun createFromParcel(source: Parcel): Cast = Cast(source)
      override fun newArray(size: Int): Array<Cast?> = arrayOfNulls(size)
    }
  }
}