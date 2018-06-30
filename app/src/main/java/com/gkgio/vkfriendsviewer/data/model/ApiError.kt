package com.gkgio.vkfriendsviewer.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class ApiError {

    @SerializedName("error")
    @Expose
    val error: Error? = null

    data class Error(
        @SerializedName("error_code")
        @Expose
        var errorCode: Int?,
        @SerializedName("error_msg")
        @Expose
        var errorMsg: String?,
        @SerializedName("request_params")
        @Expose
        var requestParams: List<RequestParam>?
    )

    data class RequestParam(
        @SerializedName("key")
        @Expose
        val key: String,
        @SerializedName("value")
        @Expose
        val value: String
    )
}