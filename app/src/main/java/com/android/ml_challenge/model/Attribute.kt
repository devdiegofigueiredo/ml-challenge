package com.android.ml_challenge.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Attribute(
    @SerializedName("value_name") val valueName: String,
    @SerializedName("name") val name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(valueName)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Attribute> {
        override fun createFromParcel(parcel: Parcel): Attribute {
            return Attribute(parcel)
        }

        override fun newArray(size: Int): Array<Attribute?> {
            return arrayOfNulls(size)
        }
    }
}