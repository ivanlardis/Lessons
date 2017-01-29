package com.example.i_larin.pixabayreader.network

import com.example.i_larin.pixabayreader.model.network.NWPixabayResponse
import com.google.gson.JsonElement
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by i_larin on 28.01.17.
 */
interface PixabayImageApi {
    @GET("/api")
    fun getImages(@Query("key") key: String, @Query("q") query: String,
                  @Query("page") pageNo: Int, @Query("per_page") pageSize: Int): Observable<NWPixabayResponse>

}