package com.gkgio.vkfriendsviewer.di.module

import dagger.Module
import dagger.Provides
import com.gkgio.vkfriendsviewer.data.api.IService
import com.gkgio.vkfriendsviewer.di.scope.PerActivity
import com.gkgio.vkfriendsviewer.ui.main.MainView
import com.gkgio.vkfriendsviewer.ui.main.MainActivity
import com.gkgio.vkfriendsviewer.ui.main.MainPresenter
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
class MainModule {

  @Provides
  @PerActivity
  fun providePresenter(service: IService, view: MainActivity) = MainPresenter<MainView>(service, view)

  @Provides
  @PerActivity
  fun provideIService(r: Retrofit): IService = r.create(IService::class.java)

  @Provides
  @PerActivity
  fun provideOkHttpClient(builder: OkHttpClient.Builder) = builder.build()

}