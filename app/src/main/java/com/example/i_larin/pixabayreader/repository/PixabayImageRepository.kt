package com.example.i_larin.pixabayreader.repository

import com.example.i_larin.pixabayreader.model.app.PixabayImagesVisual
import com.example.i_larin.pixabayreader.model.converter.PixabayImageConverter
import com.example.i_larin.pixabayreader.network.PixabayImageApi
import com.f2prateek.rx.preferences.RxSharedPreferences
import rx.Observable
import java.util.*

/**
 * Created by i_larin on 28.01.17.
 */
class PixabayImageRepository {

    private val PIXABAY_API_KEY = "4386506-63329db38400319250ee539d9"
    private val DEFAULT_TEXT = "ROBOT"

    private val api: PixabayImageApi
    private val rxSharedPreferences: RxSharedPreferences

    constructor(api: PixabayImageApi, rxSharedPreferences: RxSharedPreferences) {
        this.api = api
        this.rxSharedPreferences = rxSharedPreferences
    }

    private var pageNo = 1
    private var pageSize = 20
    private var total = -1
    private fun getTextQuery(): String {
        return rxSharedPreferences.getString("TEXT", DEFAULT_TEXT).get()!!

    }

    fun getData(query: String?): Observable<PixabayImagesVisual> {
        pageNo = 1
        if (query != null) rxSharedPreferences.getString("TEXT", DEFAULT_TEXT).set(query)
        return getData()
    }

    fun getDataMore(): Observable<PixabayImagesVisual> {
        pageNo++
        if(total>0 && total<(pageNo-1)*pageSize)
            return Observable.just(PixabayImagesVisual("", ArrayList(),true))
        return getData()
    }

    private fun getData(): Observable<PixabayImagesVisual> = api.getImages(PIXABAY_API_KEY, getTextQuery(), pageNo, pageSize)
            .map {
                total=it.total
               return@map it.hits }
            .flatMap {
                 Observable.from(it) }
            .map {

                  PixabayImageConverter.fromNetwork(it) }
            .toList()
            .map { PixabayImagesVisual(getTextQuery(), it,false) }
}