package com.gkgio.vkfriendsviewer.ui.main.profile

import android.content.Context
import com.gkgio.vkfriendsviewer.base.BasePresenter
import com.gkgio.vkfriendsviewer.base.BaseView
import com.gkgio.vkfriendsviewer.data.model.FriendInfo
import com.gkgio.vkfriendsviewer.data.model.ProfileInfo

interface ProfileContract {
  interface View : BaseView {
    fun showProfile(profile: ProfileInfo)
    fun onError(message: String)
    fun errorGetToken()
    fun showProgress()
    fun hideProgress()
  }

  interface Presenter : BasePresenter<View> {
    fun loadProfile(context: Context, id: Long)
  }
}