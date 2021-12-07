package com.nttn.pkot.data.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class SampleData(
    @Json(name = "id") val id: Int? = 0,
    @Json(name = "name") val name: String? = "",
    @Json(name = "email") val email: String? = "",
    @Json(name = "avatar") val avatar: String? = "",
    @Json(name = "content") val content: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(avatar)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SampleData> {
        override fun createFromParcel(parcel: Parcel): SampleData {
            return SampleData(parcel)
        }

        override fun newArray(size: Int): Array<SampleData?> {
            return arrayOfNulls(size)
        }
    }
}
