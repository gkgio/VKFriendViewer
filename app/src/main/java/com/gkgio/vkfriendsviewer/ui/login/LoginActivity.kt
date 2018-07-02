package com.gkgio.vkfriendsviewer.ui.login

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.gkgio.vkfriendsviewer.R
import com.gkgio.vkfriendsviewer.base.BaseActivity
import com.gkgio.vkfriendsviewer.ui.main.MainActivity
import com.gkgio.vkfriendsviewer.utils.Config
import com.gkgio.vkfriendsviewer.utils.parseRedirectUrl
import com.gkgio.vkfriendsviewer.utils.saveToken
import com.gkgio.vkfriendsviewer.utils.snackBar
import me.zhanghai.android.materialprogressbar.MaterialProgressBar

class LoginActivity : BaseActivity() {

  companion object {
    private val TAG = LoginActivity::class.simpleName
  }

  override val layoutRes: Int
    get() = R.layout.activity_login

  private lateinit var progress: MaterialProgressBar
  private lateinit var webView: WebView
  private var vkWebViewClient: VkWebViewClient? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    webView = findViewById(R.id.web)
    progress = findViewById(R.id.loginProgressbar)

    webView.isVerticalScrollBarEnabled = true
    webView.isHorizontalScrollBarEnabled = true
    webView.clearCache(true)
    vkWebViewClient = VkWebViewClient()
    webView.webViewClient = vkWebViewClient
    val builder = Uri.Builder()
    builder.scheme("https")
        .authority("oauth.vk.com")
        .appendPath("authorize")
        .appendQueryParameter("client_id", Config.CLIENT_ID)
        .appendQueryParameter("redirect_uri", Config.REDIRECT_URI)
        .appendQueryParameter("display", Config.DISPLAY)
        .appendQueryParameter("response_type", Config.RESPONSE_TYPE)
        .appendQueryParameter("scope", Config.SCOPE)
        .appendQueryParameter("v", Config.VERSION)
    webView.loadUrl(builder.toString())
    webView.visibility = View.VISIBLE

  }

  internal inner class VkWebViewClient : WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
      super.onPageStarted(view, url, favicon)
      progress.visibility = View.VISIBLE
      parseUrl(url)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
      super.onPageFinished(view, url)
      if (url != null && (url.startsWith("https://oauth.vk.com/authorize")
              || url.startsWith("https://oauth.vk.com/oauth/authorize"))) {
        progress.visibility = View.GONE
      }
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
      return false
    }
  }

  private fun parseUrl(url: String?) {
    try {
      if (url == null) {
        return
      }
      if (url.startsWith(Config.REDIRECT_URI)) {
        if (!url.contains("error")) {
          val auth = parseRedirectUrl(url)
          webView.visibility = View.GONE
          progress.visibility = View.VISIBLE

          saveToken(this, auth[0])

          startActivity(Intent(this, MainActivity::class.java))
          finish()
        } else {
          snackBar(Config.UNKNOWN_ERROR).show()
        }
      } else if (url.contains("error?err")) {
        snackBar(Config.UNKNOWN_ERROR).show()
      }
    } catch (e: Exception) {
      Log.d(TAG, Config.UNKNOWN_ERROR)
      snackBar(Config.UNKNOWN_ERROR).show()
    }

  }
}
