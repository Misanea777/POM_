package com.example.mytrashyapp.ui.library.screens.songs.models

import android.os.Parcel
import android.os.Parcelable

data class Song(
        val name: String?,
        val artist: String? = null,
        val url: String? = null,
        val image: Int = -1,
        ): Parcelable  {

        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readInt()
        ) {
        }

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel?, flags: Int) {
                dest?.writeString(name)
                dest?.writeString(artist)
                dest?.writeString(url)
                dest?.writeInt(image)
        }

        companion object CREATOR : Parcelable.Creator<Song> {
                override fun createFromParcel(parcel: Parcel): Song {
                        return Song(parcel)
                }

                override fun newArray(size: Int): Array<Song?> {
                        return arrayOfNulls(size)
                }
        }

}