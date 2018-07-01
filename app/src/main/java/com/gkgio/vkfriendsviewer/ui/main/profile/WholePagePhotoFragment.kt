package com.gkgio.vkfriendsviewer.ui.main.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.AppCompatImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.gkgio.vkfriendsviewer.R
import java.io.File


class WholePagePhotoFragment : Fragment() {

  companion object {
    val TAG = WholePagePhotoFragment::class.simpleName

    const val BUNDLE_FILE_PATH_PHOTO_SAVED = "FILE_PATH_PHOTO_SAVED"

    fun newInstance(filePath: String): WholePagePhotoFragment {
      val fragment = WholePagePhotoFragment()
      val args = Bundle()
      args.putString(BUNDLE_FILE_PATH_PHOTO_SAVED, filePath)
      fragment.arguments = args
      return fragment
    }
  }

  var fileSavedPath: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val arguments = arguments
    if (arguments != null) {
      fileSavedPath = arguments.getString(BUNDLE_FILE_PATH_PHOTO_SAVED)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_whole_page_photo, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val photoWholePage: AppCompatImageView = view.findViewById(R.id.photoWholePage)
    if (fileSavedPath != null) {
      Glide.with(this)
          .load(File(fileSavedPath))
          .transition(DrawableTransitionOptions.withCrossFade())
          .into(photoWholePage)
    }

    photoWholePage.setOnClickListener {
      fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
  }
}