package com.gkgio.vkfriendsviewer.utils

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import com.gkgio.vkfriendsviewer.R
import com.gkgio.vkfriendsviewer.utils.Config.TOKEN
import java.util.regex.Pattern

fun Activity.snackBar(text: String) = Snackbar.make(this.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT)

fun Fragment.snackBar(text: String) {
  val activity = this.activity
  if (activity != null) {
    Snackbar.make(activity.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT)
  }
}

fun showErrorAlertDialog(
    activity: Activity?,
    message: String,
    positiveCallback: DialogInterface.OnClickListener) {
  if (activity != null) {
    android.support.v7.app.AlertDialog.Builder(activity)
        .setMessage(message)
        .setPositiveButton(R.string.yes, positiveCallback)
        .create()
        .show()
  } else {
    Log.e(TAG, "Can't show dialog without activity")
  }
}

fun saveToken(context: Context, token: String) {
  PreferenceManager.getDefaultSharedPreferences(context)
      .edit().putString(TOKEN, token)
      .apply()
}

fun getToken(context: Context): String? {
  return PreferenceManager.getDefaultSharedPreferences(context)
      .getString(TOKEN, null)
}

fun isEmptyString(string: String?): Boolean {
  return string == null || string.isEmpty()
}

fun parseRedirectUrl(url: String): Array<String> {
  val access_token = extractPattern(url, "access_token=(.*?)&")
  val user_id = extractPattern(url, "user_id=(\\d*)")
  if (user_id == null || user_id.length == 0 || access_token == null || access_token.length == 0) {
    throw Exception("Failed to parse redirect url $url")
  }
  return arrayOf(access_token, user_id)
}

fun extractPattern(string: String, pattern: String): String? {
  val p = Pattern.compile(pattern)
  val m = p.matcher(string)
  return if (!m.find()) null else m.toMatchResult().group(1)
}