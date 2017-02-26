package com.example.i_larin.pixabayreader.network

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by black-sony on 19.02.17.
 */
class CacheInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val cacheControl = CacheControl
                .Builder()
                .maxAge(7, TimeUnit.DAYS)
                .maxStale(1, TimeUnit.DAYS)
                .build()

        var request = chain
                .request()
                .newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()

        return chain
                .proceed(request)


    }
}