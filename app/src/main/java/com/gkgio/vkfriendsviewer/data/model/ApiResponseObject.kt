package com.gkgio.vkfriendsviewer.data.model

import com.google.gson.annotations.SerializedName

open class ApiResponseObject<T> (
    @SerializedName("response")
    val response: T? = null
) : ApiError()