package com.example.i_larin.pixabayreader.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by parth on 11/17/16.
 */

public class PixabayCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        CacheControl cacheControl = new CacheControl.Builder().maxAge(7, TimeUnit.DAYS)
                .maxStale(1, TimeUnit.DAYS).build();
        request = request.newBuilder().header("Cache-Control", cacheControl.toString()).build();
        return chain.proceed(request);
    }
}
