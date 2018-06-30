package com.gkgio.vkfriendsviewer.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import com.gkgio.vkfriendsviewer.di.scope.PerApp

@Module
class AppModule(private val application: Application) {
    @Provides @PerApp
    fun application(): Application = application

    @Provides @PerApp
    fun context(): Context = application
}