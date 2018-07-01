package com.gkgio.vkfriendsviewer.data.model

import com.google.gson.annotations.SerializedName

class City(
    @SerializedName("id")
    var id: Int,

    @SerializedName("title")
    var title: String
)