package com.example.i_larin.pixabayreader.network

import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

/**
 * Created by black-sony on 29.01.17.
 */
class OkHttpClientFactory(val context: Context) {


    fun provideOkHttpClient() = with(OkHttpClient.Builder()) {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message -> Timber.d("NetworkRequest", message) }
                .setLevel(HttpLoggingInterceptor.Level.BASIC)
        listOf<Interceptor>(httpLoggingInterceptor).forEach {
            addInterceptor(it)
        }

        val httpLoggingNetworkInterceptor = HttpLoggingInterceptor { message -> Timber.d("NetworkCall", message) }
                .setLevel(HttpLoggingInterceptor.Level.BASIC)

        listOf<Interceptor>(httpLoggingNetworkInterceptor).forEach {
            addNetworkInterceptor(it)
        }

        addInterceptor(CacheInterceptor())
        val CACHE_SIZE = 10 * 1024 * 1024L
        cache(Cache(context.cacheDir, CACHE_SIZE))
        build()
    }


}