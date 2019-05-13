package com.sun.moviesun.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PersonDetail(
    val id: Int,
    val name: String?,
    val birthday: String? = "",
    @SerializedName("known_for_department")
    val knownForDepartment: String? = "",
    @SerializedName("place_of_birth")
    val placeOfBirth: String? = "",
    @SerializedName("also_known_as")
    val alsoKnownAs: List<String>?,
    val biography: String? = "",
    @SerializedName("profile_path")
    val profilePath: String?
) : Parcelable {
  constructor(source: Parcel) : this(
      source.readInt(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.createStringArrayList(),
      source.readString(),
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeInt(id)
    writeString(name)
    writeString(birthday)
    writeString(knownForDepartment)
    writeString(placeOfBirth)
    writeStringList(alsoKnownAs)
    writeString(biography)
    writeString(profilePath)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<PersonDetail> = object : Parcelable.Creator<PersonDetail> {
      override fun createFromParcel(source: Parcel): PersonDetail = PersonDetail(source)
      override fun newArray(size: Int): Array<PersonDetail?> = arrayOfNulls(size)
    }
  }
}
