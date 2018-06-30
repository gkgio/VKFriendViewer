package com.gkgio.vkfriendsviewer.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.gkgio.vkfriendsviewer.di.scope.PerActivity
import com.gkgio.vkfriendsviewer.ui.main.MainActivity

@Module
abstract class ActivityModule {

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MainModule::class))
    abstract fun activityInjector(): MainActivity
}