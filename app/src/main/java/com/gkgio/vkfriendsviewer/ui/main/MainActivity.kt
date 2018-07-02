package com.gkgio.vkfriendsviewer.ui.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import com.gkgio.vkfriendsviewer.R
import com.gkgio.vkfriendsviewer.base.BaseActivity
import com.gkgio.vkfriendsviewer.data.model.FriendInfo
import com.gkgio.vkfriendsviewer.di.component.DaggerMainComponent
import com.gkgio.vkfriendsviewer.ui.login.LoginActivity
import com.gkgio.vkfriendsviewer.ui.main.profile.ProfileActivity
import com.gkgio.vkfriendsviewer.ui.main.profile.ProfileActivity.Companion.BUNDLE_PROFILE_ID
import com.gkgio.vkfriendsviewer.utils.showErrorAlertDialog

import com.gkgio.vkfriendsviewer.utils.snackBar
import me.zhanghai.android.materialprogressbar.MaterialProgressBar
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {

  @Inject
  lateinit var presenter: MainPresenter

  override val layoutRes: Int
    get() = R.layout.activity_main

  private lateinit var emptyItemsStub: TextView
  private lateinit var layoutSwipeRefresh: SwipeRefreshLayout
  private lateinit var progress: MaterialProgressBar
  private lateinit var rvFriends: RecyclerView

  private lateinit var recyclerFriendsAdapter: RecyclerFriendsAdapter

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
    rvFriends = findViewById(R.id.rvFriends)

    val layoutManager = LinearLayoutManager(this)
    layoutManager.orientation = LinearLayoutManager.VERTICAL
    rvFriends.layoutManager = layoutManager
    rvFriends.itemAnimator = DefaultItemAnimator()

    recyclerFriendsAdapter = RecyclerFriendsAdapter(::openProfile)
    rvFriends.layoutManager = LinearLayoutManager(this)
    rvFriends.adapter = recyclerFriendsAdapter

    layoutSwipeRefresh.setOnRefreshListener {
      presenter.loadFriends(this, true)
    }
    presenter.loadFriends(this, false)
  }

  override fun showFriends(friends: List<FriendInfo>) {
    if (!friends.isEmpty()) {
      recyclerFriendsAdapter.setFriends(friends)
      emptyItemsStub.visibility = View.GONE
    } else {
      emptyItemsStub.visibility = View.VISIBLE
    }
  }

  private fun openProfile(id: Long) {
    val bundle = Bundle()
    bundle.putLong(BUNDLE_PROFILE_ID, id)
    startActivity(Intent(this, ProfileActivity::class.java).putExtras(bundle))
    overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
  }

  override fun onError(message: String) {
    snackBar(message).show()
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
