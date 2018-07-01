package com.gkgio.vkfriendsviewer.data.api

import com.gkgio.vkfriendsviewer.data.model.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IService {
  @GET("friends.get")
  fun getFriends(
      @Query("order") order: String,
      @Query("fields") fields: String,
      @Query("v") v: String,
      @Query("access_token") token: String
  ): Observable<ApiResponseObject<ApiResponseList<FriendInfo>>>

  @GET("users.get")
  fun getProfileInfo(
      @Query("fields") fields: String,
      @Query("v") v: String,
      @Query("access_token") token: String,
      @Query("user_id") id: String?
  ): Observable<ApiResponseObject<Array<ProfileInfo>>>
}
