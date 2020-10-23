package com.codelab.githubuser.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

// Response from GITHUB API and Entity of ROOM DATABASE
@Entity(tableName = "user_favorite_table")
@Parcelize
data class DetailUser(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var photoProfile: String,
    var username:String,
    var name: String,
    var company: String,
    var location: String,
    var followers: Int,
    var following: Int,
    var homepage: String
) : Parcelable