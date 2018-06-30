package com.gkgio.vkfriendsviewer.ui.main

import android.content.Context
import com.gkgio.vkfriendsviewer.base.BasePresenter
import com.gkgio.vkfriendsviewer.base.BaseView
import com.gkgio.vkfriendsviewer.data.model.FriendInfo

interface MainContract {
  interface View : BaseView {
    fun onError(message: String)
    fun showFriends(friends: List<FriendInfo>)
    fun errorGetToken()
    fun showProgress(isSwipeRefresh: Boolean)
    fun hideProgress()
  }

  interface Presenter : BasePresenter<View> {
    fun loadFriends(context: Context, isSwipeRefresh: Boolean)
  }
}
