package com.gkgio.vkfriendsviewer.ui.main.profile

import android.content.Context
import com.gkgio.vkfriendsviewer.data.api.IService
import com.gkgio.vkfriendsviewer.data.model.ApiResponseList
import com.gkgio.vkfriendsviewer.data.model.ApiResponseObject
import com.gkgio.vkfriendsviewer.data.model.FriendInfo
import com.gkgio.vkfriendsviewer.data.model.ProfileInfo
import com.gkgio.vkfriendsviewer.ui.main.MainContract
import com.gkgio.vkfriendsviewer.utils.getToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfilePresenter @Inject constructor(private val iService: IService) : ProfileContract.Presenter {
  private var disposable: Disposable? = null
  private var view: ProfileContract.View? = null

  override fun attachView(view: ProfileContract.View) {
    this.view = view
  }

  override fun loadProfile(context: Context, id: Long) {
    disposable?.dispose()

    val token: String? = getToken(context)
    if (token != null) {
      view?.showProgress()

      disposable = iService.getProfileInfo("photo_400_orig,city,bdate,education", "5.80", token, id.toString())
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe({ res: ApiResponseObject<Array<ProfileInfo>> ->
            view?.hideProgress()
            if (res.response?.get(0) != null) {
              view?.showProfile(res.response[0])
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