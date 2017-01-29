package com.example.i_larin.pixabayreader.network

import android.content.Context
import android.util.Log
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by black-sony on 29.01.17.
 */
class OkHttpClientFactory (val context:Context){


        fun provideOkHttpClient(): OkHttpClient {
            val CACHE_SIZE = 10 * 1024 * 1024
            val httpLoggingInterceptor = HttpLoggingInterceptor { message -> Log.d("NetworkRequest", message) }
                    .setLevel(HttpLoggingInterceptor.Level.BASIC)
            val okHttpBuilder = OkHttpClient.Builder()
            for (interceptor in  listOf<Interceptor>(httpLoggingInterceptor)) {
                okHttpBuilder.addInterceptor(interceptor)
            }


            val httpLoggingNetworkInterceptor = HttpLoggingInterceptor { message -> Log.d("NetworkCall", message) }
                    .setLevel(HttpLoggingInterceptor.Level.BASIC)


            for (networkInterceptor in listOf<Interceptor>(httpLoggingNetworkInterceptor)) {
                okHttpBuilder.addNetworkInterceptor(networkInterceptor)
            }
            okHttpBuilder.addInterceptor(PixabayCacheInterceptor())
            val cacheDir = context.getCacheDir()
            okHttpBuilder.cache(Cache(cacheDir, CACHE_SIZE.toLong()))
            return okHttpBuilder.build()
        }



}