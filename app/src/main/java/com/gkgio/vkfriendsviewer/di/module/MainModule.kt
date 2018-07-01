package com.gkgio.vkfriendsviewer.di.module

import dagger.Module
import dagger.Provides
import com.gkgio.vkfriendsviewer.data.api.IService
import com.gkgio.vkfriendsviewer.di.scope.ActivityScope
import com.gkgio.vkfriendsviewer.ui.main.MainContract
import com.gkgio.vkfriendsviewer.ui.main.MainPresenter
import com.gkgio.vkfriendsviewer.ui.main.profile.ProfileContract
import com.gkgio.vkfriendsviewer.ui.main.profile.ProfilePresenter

@Module
class MainModule {

  @Provides
  @ActivityScope
  fun provideMainPresenter(restService: IService): MainContract.Presenter {
    return MainPresenter(restService)
  }

  @Provides
  @ActivityScope
  fun provideProfilePresenter(restService: IService): ProfileContract.Presenter {
    return ProfilePresenter(restService)
  }

}