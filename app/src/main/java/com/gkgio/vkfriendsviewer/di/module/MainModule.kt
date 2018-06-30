package com.gkgio.vkfriendsviewer.di.module

import dagger.Module
import dagger.Provides
import com.gkgio.vkfriendsviewer.data.api.IService
import com.gkgio.vkfriendsviewer.di.scope.ActivityScope
import com.gkgio.vkfriendsviewer.ui.main.MainContract
import com.gkgio.vkfriendsviewer.ui.main.MainPresenter

@Module
class MainModule {

  @Provides
  @ActivityScope
  fun provideMainPresenter(restService: IService): MainContract.Presenter {
    return MainPresenter(restService)
  }

}