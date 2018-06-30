package com.gkgio.vkfriendsviewer.data.model

import com.google.gson.annotations.SerializedName

open class ApiResponseList<T>(
                    @SerializedName("count")
                    val count: Int? = null,
                    @SerializedName("items")
                    val items: List<T>
            )