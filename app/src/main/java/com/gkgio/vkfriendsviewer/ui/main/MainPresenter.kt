package com.gkgio.vkfriendsviewer.ui.main

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.gkgio.vkfriendsviewer.data.api.IService
import com.gkgio.vkfriendsviewer.data.model.ApiResponseList
import com.gkgio.vkfriendsviewer.data.model.ApiResponseObject
import com.gkgio.vkfriendsviewer.data.model.FriendInfo
import com.gkgio.vkfriendsviewer.di.scope.PerActivity
import com.gkgio.vkfriendsviewer.utils.getToken
import io.reactivex.disposables.Disposable
import javax.inject.Inject


@PerActivity
class MainPresenter<T : MainView> @Inject constructor(private val iService: IService,
                                                      private val view: T) {

  private var subscription: Disposable? = null

  fun loadFriends(context: Context, isSwipeRefresh: Boolean) {
    subscription?.dispose()

    val token: String? = getToken(context)
    if (token != null) {
      view.showProgress(isSwipeRefresh)

      subscription = iService.getFriends(token, 0)
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe({ res: ApiResponseObject<ApiResponseList<FriendInfo>> ->
            view.hideProgress()
            if (res.response != null) {
              view.showFriends(res.response.items)
            }
          }, { throwable ->
            view.hideProgress()
            view.onError(throwable.message ?: "")
          })
    } else {
      view.errorGetToken()
    }
  }

  fun onDetachView() {
    subscription?.dispose()
  }
}