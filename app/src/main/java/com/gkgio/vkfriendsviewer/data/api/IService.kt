package com.gkgio.vkfriendsviewer.data.api

import com.gkgio.vkfriendsviewer.data.model.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IService {
    @GET("friends.get?order=name&count=10&fields=photo_100&v=5.80")
    fun getFriends(
        @Query("access_token") token: String,
        @Query("offset") offset: Int
    ): Observable<ApiResponseObject<ApiResponseList<FriendInfo>>>
}
