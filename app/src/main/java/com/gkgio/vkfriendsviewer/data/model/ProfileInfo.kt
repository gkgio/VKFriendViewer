package com.gkgio.vkfriendsviewer.data.model

import com.google.gson.annotations.SerializedName

class ProfileInfo(
    @SerializedName("id")
    val id: Long,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("photo_400_orig")
    val photoOrig: String,

    @SerializedName("bdate")
    val birthdayDate: String,

    @SerializedName("city")
    val city: City?,

    @SerializedName("university_name")
    val universityName: String?,

    @SerializedName("faculty_name")
    val facultyName: String?,

    @SerializedName("graduation")
    val graduation: Int?
)