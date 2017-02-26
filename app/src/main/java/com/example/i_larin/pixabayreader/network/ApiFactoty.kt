package com.example.i_larin.pixabayreader.network

import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * Created by i_larin on 28.01.17.
 */

class ApiFactoty {
    private var retrofit: Retrofit

    constructor(baseUrl: String,
                converterFactory: Converter.Factory,
                callAdapterFactory: CallAdapter.Factory,
                okHttpClientFactory: OkHttpClientFactory) {
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClientFactory.provideOkHttpClient())
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(converterFactory)
                .build()
    }

    fun create(java: Class<PixabayImageApi>) = retrofit.create(java)

}