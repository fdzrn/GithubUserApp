package com.submission.consumerapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// Response from GITHUB API and Entity of ROOM DATABASE
@Parcelize
data class DetailUser(
    var id: Int,
    var photoProfile: String,
    var username: String,
    var location: String
) : Parcelable