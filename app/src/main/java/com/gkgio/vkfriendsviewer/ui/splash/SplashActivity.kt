package com.gkgio.vkfriendsviewer.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.gkgio.vkfriendsviewer.R
import com.gkgio.vkfriendsviewer.ui.login.LoginActivity
import com.gkgio.vkfriendsviewer.ui.main.MainActivity
import com.gkgio.vkfriendsviewer.utils.Config
import com.gkgio.vkfriendsviewer.utils.getToken

class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    Handler().postDelayed({
      run {
        if (getToken(this) != null) {
          startActivity(Intent(this, MainActivity::class.java))
        } else {
          startActivity(Intent(this, LoginActivity::class.java))
        }
      }
      finish()
    }, Config.SHOW_SPLASH_DELAY_MILLIS)

  }
}