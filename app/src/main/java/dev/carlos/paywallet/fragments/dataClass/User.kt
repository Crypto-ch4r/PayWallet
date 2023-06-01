package dev.carlos.paywallet.fragments.dataClass

import android.os.Parcel
import android.os.Parcelable

data class User(
    var control_num:String? = null,
    var password:String? = null,
    var names:String? = null,
    var lastnames:String? = null,
    var rfid:String? = null) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(control_num)
        parcel.writeString(password)
        parcel.writeString(names)
        parcel.writeString(lastnames)
        parcel.writeString(rfid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}