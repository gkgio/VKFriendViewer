package com.gkgio.vkfriendsviewer.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import com.gkgio.vkfriendsviewer.AndroidApplication
import com.gkgio.vkfriendsviewer.BuildConfig
import com.gkgio.vkfriendsviewer.data.api.IService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule(private val application: AndroidApplication) {
  @Provides
  @Singleton
  fun provideContext(): Context = application.applicationContext

  @Provides
  @Singleton
  fun provideHttpLogging(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG)
      HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .followSslRedirects(true)
        .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
  }

  @Provides
  @Singleton
  fun provideRestService(retrofit: Retrofit): IService = retrofit.create(IService::class.java)
}