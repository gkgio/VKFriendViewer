package com.gkgio.vkfriendsviewer.data.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class FriendInfo(
    @SerializedName("id")
    val id: Long,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("photo_100")
    val photo: String
)