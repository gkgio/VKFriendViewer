package com.gkgio.vkfriendsviewer.di.component

import com.gkgio.vkfriendsviewer.di.module.MainModule
import com.gkgio.vkfriendsviewer.di.scope.ActivityScope
import com.gkgio.vkfriendsviewer.ui.main.MainActivity
import com.gkgio.vkfriendsviewer.ui.main.profile.ProfileActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(MainModule::class), dependencies = arrayOf(AppComponent::class))
interface MainComponent {
  fun inject(activity: MainActivity)

  fun inject(profileFragment: ProfileActivity)
}