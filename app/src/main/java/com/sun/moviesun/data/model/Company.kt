package com.sun.moviesun.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Company (
    val id: Int,
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?,
    @SerializedName("logo_path")
    val logoPath: String?
) : Parcelable {
  constructor(source: Parcel) : this(
      source.readInt(),
      source.readString(),
      source.readString(),
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeInt(id)
    writeString(name)
    writeString(originCountry)
    writeString(logoPath)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<Company> = object : Parcelable.Creator<Company> {
      override fun createFromParcel(source: Parcel): Company = Company(source)
      override fun newArray(size: Int): Array<Company?> = arrayOfNulls(size)
    }
  }
}