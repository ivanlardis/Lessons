package com.example.i_larin.pixabayreader.di.model

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.example.i_larin.pixabayreader.network.*
import com.f2prateek.rx.preferences.RxSharedPreferences
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Created by black-sony on 29.01.17.
 */
@Module
class ModelModule(val context: Context) {
    val PIXABAY_BASE_URL = "https://pixabay.com"

    @Provides
    @Singleton
    fun provideApi(apiFactoty: ApiFactoty): PixabayImageApi = apiFactoty.create(PixabayImageApi::class.java!!)

    @Provides
    @Singleton
    fun provideApiFactoty(converterFactory: Converter.Factory,
                          callAdapterFactory: CallAdapter.Factory, okHttpClientFactory: OkHttpClientFactory): ApiFactoty =
            ApiFactoty(PIXABAY_BASE_URL, converterFactory, callAdapterFactory, okHttpClientFactory)

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context);

    @Provides
    @Singleton
    fun provideRxSharedPreferences(preferences: SharedPreferences): RxSharedPreferences =
            RxSharedPreferences.create(preferences)


    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create(GsonBuilder().create())

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJavaCallAdapterFactory.create()


    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClientFactory(context)

}