package com.gkgio.vkfriendsviewer.di.module

import com.gkgio.vkfriendsviewer.BuildConfig
import dagger.Module
import dagger.Provides
import com.gkgio.vkfriendsviewer.di.scope.PerApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
@Module
class ApiModule {

  @Provides
  @PerApp
  fun provideRetrofit(): Retrofit = Retrofit.Builder()
      .baseUrl(BuildConfig.API_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()

  @Provides
  @PerApp
  fun provideHttpClient(): OkHttpClient.Builder {
    val builder: OkHttpClient.Builder = OkHttpClient.Builder()
    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
      interceptor.level = HttpLoggingInterceptor.Level.BODY
    }
    builder.interceptors().add(interceptor)
    return builder
  }
}