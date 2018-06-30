package com.gkgio.vkfriendsviewer

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import com.gkgio.vkfriendsviewer.di.component.DaggerAppComponent

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent.builder().app(this).build()
}