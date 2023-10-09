package com.david.githubmobile.data.local.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "user")
data class UserEntity(
    @field:Json(name = "id") @PrimaryKey val id: Int? = 0,

    @field:Json(name = "login") val username: String? = "",

    @field:Json(name = "name") val name: String? = "",

    @field:Json(name = "location") val location: String? = "",

    @field:Json(name = "company") val company: String? = "",

    @field:Json(name = "public_repos") val repository: Int? = 0,

    @field:Json(name = "followers") val follower: Int? = 0,

    @field:Json(name = "following") val following: Int? = 0,

    @field:Json(name = "avatar_url") val avatar: String? = "",

    var isFavorite: Boolean? = false,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(username)
        parcel.writeString(name)
        parcel.writeString(location)
        parcel.writeString(company)
        parcel.writeValue(repository)
        parcel.writeValue(follower)
        parcel.writeValue(following)
        parcel.writeString(avatar)
        parcel.writeValue(isFavorite)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserEntity> {
        override fun createFromParcel(parcel: Parcel): UserEntity {
            return UserEntity(parcel)
        }

        override fun newArray(size: Int): Array<UserEntity?> {
            return arrayOfNulls(size)
        }
    }
}
