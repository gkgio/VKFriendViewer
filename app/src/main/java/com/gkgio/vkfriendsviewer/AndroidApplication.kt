package com.gkgio.vkfriendsviewer

import android.app.Application
import com.gkgio.vkfriendsviewer.di.component.AppComponent
import com.gkgio.vkfriendsviewer.di.component.DaggerAppComponent
import com.gkgio.vkfriendsviewer.di.module.AppModule

class AndroidApplication: Application() {

    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}