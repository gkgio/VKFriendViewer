package com.gkgio.vkfriendsviewer.ui.main

import com.gkgio.vkfriendsviewer.data.model.FriendInfo

interface MainView {
    fun onError(mesage:String)
    fun showFriends(friends: List<FriendInfo>)
    fun errorGetToken()
    fun showProgress(isSwipeRefresh: Boolean)
    fun hideProgress()
}