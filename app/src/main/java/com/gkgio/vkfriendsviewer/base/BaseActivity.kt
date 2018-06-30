package com.gkgio.vkfriendsviewer.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.gkgio.vkfriendsviewer.AndroidApplication
import com.gkgio.vkfriendsviewer.di.component.AppComponent

abstract class BaseActivity : AppCompatActivity() {
    protected abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    fun getAppComponent() : AppComponent? = (applicationContext as AndroidApplication).appComponent
}
