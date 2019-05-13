package com.sun.moviesun.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CompanyDetail(
    val id: Int,
    val name: String?,
    val homepage: String?,
    val headquarters: String?,
    val description: String?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("origin_country")
    val originCountry: String?,
    @SerializedName("parent_company")
    val parentCompany: String?
) : Parcelable {

  constructor(source: Parcel) : this(
      source.readInt(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeInt(id)
    writeString(name)
    writeString(homepage)
    writeString(headquarters)
    writeString(description)
    writeString(logoPath)
    writeString(originCountry)
    writeString(parentCompany)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<CompanyDetail> = object : Parcelable.Creator<CompanyDetail> {
      override fun createFromParcel(source: Parcel): CompanyDetail = CompanyDetail(source)
      override fun newArray(size: Int): Array<CompanyDetail?> = arrayOfNulls(size)
    }
  }
}
