package com.gkgio.vkfriendsviewer.di.component

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import com.gkgio.vkfriendsviewer.App
import com.gkgio.vkfriendsviewer.di.module.ActivityModule
import com.gkgio.vkfriendsviewer.di.module.AppModule
import com.gkgio.vkfriendsviewer.di.module.ApiModule
import com.gkgio.vkfriendsviewer.di.scope.PerApp

@PerApp
@Component(
        modules = arrayOf(
                AppModule::class,
                AndroidSupportInjectionModule::class,
                ApiModule::class,
                ActivityModule::class
        ))
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance fun app(app: App): Builder
        fun appModule(module: AppModule): Builder
        fun dataModule(module: ApiModule): Builder
        fun build(): AppComponent
    }
}