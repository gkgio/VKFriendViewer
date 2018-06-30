package com.gkgio.vkfriendsviewer.ui.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

import dagger.android.support.DaggerAppCompatActivity
import com.gkgio.vkfriendsviewer.R
import com.gkgio.vkfriendsviewer.base.BaseActivity
import com.gkgio.vkfriendsviewer.data.model.FriendInfo
import com.gkgio.vkfriendsviewer.di.component.DaggerMainComponent
import com.gkgio.vkfriendsviewer.ui.login.LoginActivity
import com.gkgio.vkfriendsviewer.utils.showErrorAlertDialog

import com.gkgio.vkfriendsviewer.utils.snackBar
import me.zhanghai.android.materialprogressbar.MaterialProgressBar
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {

  private lateinit var emptyItemsStub: TextView
  private lateinit var layoutSwipeRefresh: SwipeRefreshLayout
  private lateinit var progress: MaterialProgressBar

  @Inject
  lateinit var presenter: MainPresenter

  override val layoutRes: Int
    get() = R.layout.activity_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerMainComponent.builder()
        .appComponent(getAppComponent())
        .build()
        .inject(this)

    presenter.attachView(this)
    emptyItemsStub = findViewById(R.id.emptyItemsStub)
    layoutSwipeRefresh = findViewById(R.id.layoutSwipeRefresh)
    progress = findViewById(R.id.mainProgressbar)

    layoutSwipeRefresh.setOnRefreshListener {
      presenter.loadFriends(this, true)
    }
    presenter.loadFriends(this, false)
  }

  override fun showFriends(friends: List<FriendInfo>) {
    if (!friends.isEmpty()) {
      emptyItemsStub.visibility = View.GONE
    } else {
      emptyItemsStub.visibility = View.VISIBLE
    }
  }

  override fun onError(mesage: String) {
    snackBar(mesage)
  }

  override fun errorGetToken() {
    showErrorAlertDialog(this,
        this.resources.getString(R.string.error_get_token),
        DialogInterface.OnClickListener { dialogInterface, i ->
          startActivity(Intent(this, LoginActivity::class.java))
        })
  }

  override fun showProgress(isSwipeRefresh: Boolean) {
    if (!isSwipeRefresh) progress.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    layoutSwipeRefresh.isRefreshing = false
    progress.visibility = View.GONE
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.detachView()
  }
}