package com.gkgio.vkfriendsviewer.ui.main.profile

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.gkgio.vkfriendsviewer.R
import com.gkgio.vkfriendsviewer.base.BaseActivity
import com.gkgio.vkfriendsviewer.data.model.ProfileInfo
import com.gkgio.vkfriendsviewer.di.component.DaggerMainComponent
import com.gkgio.vkfriendsviewer.ui.login.LoginActivity
import com.gkgio.vkfriendsviewer.utils.isEmptyString
import com.gkgio.vkfriendsviewer.utils.showErrorAlertDialog
import com.gkgio.vkfriendsviewer.utils.snackBar
import com.gkgio.vkfriendsviewer.widgets.ToolbarOneLine
import me.zhanghai.android.materialprogressbar.MaterialProgressBar
import javax.inject.Inject
import android.graphics.Bitmap
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStoragePublicDirectory
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.io.File
import java.io.FileOutputStream


class ProfileActivity : BaseActivity(), ProfileContract.View {
  companion object {
    const val BUNDLE_PROFILE_ID = "PROFILE_ID"
    val storageDir = File(getExternalStoragePublicDirectory(DIRECTORY_PICTURES), "/VK_FOLDER_IMAGE")
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
    if (profile.city != null && !isEmptyString(profile.city.title)) {
      tvCity.visibility = View.VISIBLE
      tvCity.text = resources.getString(R.string.profile_city, profile.city.title)
    }

    tvBirthdayDate.text = resources.getString(R.string.profile_birthday_date, profile.birthdayDate)

    if (!isEmptyString(profile.universityName)) {
      tvPhoneNumber.visibility = View.VISIBLE
      tvPhoneNumber.text = resources.getString(R.string.profile_phone_number, profile.universityName)
    }

    val savedFileName = String.format("%s%d.png", profile.lastName, profile.id)

    if (!isFileWithPhoto(savedFileName)) {
      loadPhotoUseLink(savedFileName, profile.photoOrig)
    } else {
      loadPhotoUseFilePath(null, savedFileName)
    }

  }

  private fun loadPhotoUseLink(savedFileName: String, link: String) {
    var savedImagePath: String? = null
    Glide.with(this)
        .asBitmap()
        .load(link)
        .into(object : SimpleTarget<Bitmap>(400, 400) {
          override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            savedImagePath = saveImage(resource, savedFileName)
            loadPhotoUseFilePath(savedImagePath, null)
          }
        })
  }

  private fun loadPhotoUseFilePath(savedImagePathNew: String?, savedFileName: String?) {
    if (savedImagePathNew != null || savedFileName != null) {
      val savedImagePath: String
      if (savedImagePathNew == null) {
        val imageFile = File(storageDir, savedFileName)
        savedImagePath = imageFile.absolutePath
      } else {
        savedImagePath = savedImagePathNew
      }
      Glide.with(this)
          .load(File(savedImagePath))
          .transition(DrawableTransitionOptions.withCrossFade())
          .into(userPhoto)
    }
  }

  private fun isFileWithPhoto(savedFileName: String): Boolean {
    if (!storageDir.exists()) {
      return false
    }

    val imageFile = File(storageDir, savedFileName)
    val savedImagePath = imageFile.absolutePath
    return File(savedImagePath).exists()
  }

  private fun saveImage(image: Bitmap, savedFileName: String): String? {
    var savedImagePath: String? = null

    var success = true
    if (!storageDir.exists()) {
      success = storageDir.mkdirs()
    }
    if (success) {
      val imageFile = File(storageDir, savedFileName)
      savedImagePath = imageFile.absolutePath
      try {
        val fOut = FileOutputStream(imageFile)
        image.compress(Bitmap.CompressFormat.PNG, 100, fOut)
        fOut.close()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
    return savedImagePath
  }

  private fun openPhotoWholePage() {

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