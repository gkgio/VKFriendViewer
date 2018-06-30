package com.gkgio.vkfriendsviewer.di.component

import dagger.Component
import com.gkgio.vkfriendsviewer.di.module.AppModule
import com.gkgio.vkfriendsviewer.data.api.IService
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun restService(): IService
}