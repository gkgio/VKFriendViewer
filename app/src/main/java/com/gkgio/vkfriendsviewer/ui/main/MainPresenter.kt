package com.gkgio.vkfriendsviewer.ui.main

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import com.gkgio.vkfriendsviewer.data.api.IService
import com.gkgio.vkfriendsviewer.data.model.ApiResponseList
import com.gkgio.vkfriendsviewer.data.model.ApiResponseObject
import com.gkgio.vkfriendsviewer.data.model.FriendInfo
import com.gkgio.vkfriendsviewer.utils.getToken
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainPresenter @Inject constructor(private val iService: IService) : MainContract.Presenter {

  private var disposable: Disposable? = null
  private var view: MainContract.View? = null

  override fun attachView(view: MainContract.View) {
    this.view = view
  }

  override fun loadFriends(context: Context, isSwipeRefresh: Boolean) {
    disposable?.dispose()

    val token: String? = getToken(context)
    if (token != null) {
      view?.showProgress(isSwipeRefresh)

      disposable = iService.getFriends("name", "photo_100", "5.80", token)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe({ res: ApiResponseObject<ApiResponseList<FriendInfo>> ->
            view?.hideProgress()
            if (res.response != null) {
              view?.showFriends(res.response.items)
            }
          }, { throwable ->
            view?.hideProgress()
            view?.onError(throwable.message ?: "")
          })
    } else {
      view?.errorGetToken()
    }

  }

  override fun detachView() {
    view = null
    disposable?.dispose()
  }
}