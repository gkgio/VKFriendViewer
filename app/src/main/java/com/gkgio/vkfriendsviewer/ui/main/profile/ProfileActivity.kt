package com.gkgio.vkfriendsviewer.ui.main.profile

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.gkgio.vkfriendsviewer.R
import com.gkgio.vkfriendsviewer.base.BaseActivity
import com.gkgio.vkfriendsviewer.data.model.FriendInfo
import com.gkgio.vkfriendsviewer.data.model.ProfileInfo
import com.gkgio.vkfriendsviewer.di.component.DaggerMainComponent
import com.gkgio.vkfriendsviewer.ui.login.LoginActivity
import com.gkgio.vkfriendsviewer.ui.main.MainPresenter
import com.gkgio.vkfriendsviewer.utils.showErrorAlertDialog
import com.gkgio.vkfriendsviewer.utils.snackBar
import com.gkgio.vkfriendsviewer.widgets.ToolbarOneLine
import me.zhanghai.android.materialprogressbar.MaterialProgressBar
import javax.inject.Inject

class ProfileActivity : BaseActivity(), ProfileContract.View {
  companion object {
    public const val BUNDLE_PROFILE_ID = "PROFILE_ID"
  }

  @Inject
  lateinit var presenter: ProfilePresenter

  override val layoutRes: Int
    get() = R.layout.activity_profile

  private lateinit var progress: MaterialProgressBar
  private lateinit var userPhoto: AppCompatImageView
  private lateinit var tvName: TextView
  private lateinit var tvCity: TextView
  private lateinit var tvBirthdayDate: TextView
  private lateinit var tvPhoneNumber: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerMainComponent.builder()
        .appComponent(getAppComponent())
        .build()
        .inject(this)

    presenter.attachView(this)

    progress = findViewById(R.id.profileProgressbar)
    userPhoto = findViewById(R.id.userPhoto)
    tvName = findViewById(R.id.tvName)
    tvCity = findViewById(R.id.tvCity)
    tvBirthdayDate = findViewById(R.id.tvBirthdayDate)
    tvPhoneNumber = findViewById(R.id.tvPhoneNumber)

    val toolbar: ToolbarOneLine = findViewById(R.id.profileToolbar)
    toolbar.setOnBackClickListener { onBackPressed() }

    val profileId = intent.getLongExtra(BUNDLE_PROFILE_ID, -1L)
    if (profileId != -1L) {
      presenter.loadProfile(this, profileId)
    }
  }

  override fun showProfile(profile: ProfileInfo) {
    tvName.text = resources.getString(
        R.string.profile_name,
        profile.firstName, profile.lastName
    )
    if (profile.city?.title != null) {
      tvCity.visibility = View.VISIBLE
      tvCity.text = resources.getString(R.string.profile_city, profile.city.title)
    }

    tvBirthdayDate.text = resources.getString(R.string.profile_birthday_date, profile.birthdayDate)

    if (profile.universityName != null) {
      tvPhoneNumber.visibility = View.VISIBLE
      tvPhoneNumber.text = resources.getString(R.string.profile_phone_number, profile.universityName)
    }

    Glide.with(this)
        .setDefaultRequestOptions(
            RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .dontAnimate()
        )
        .load(profile.photoOrig)
        .into(userPhoto)
  }

  override fun onError(message: String) {
    snackBar(message)
  }

  override fun errorGetToken() {
    showErrorAlertDialog(this,
        this.resources.getString(R.string.error_get_token),
        DialogInterface.OnClickListener { dialogInterface, i ->
          startActivity(Intent(this, LoginActivity::class.java))
        })
  }

  override fun showProgress() {
    progress.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    progress.visibility = View.GONE
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.detachView()
  }

  override fun onBackPressed() {
    super.onBackPressed()
    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
  }
}