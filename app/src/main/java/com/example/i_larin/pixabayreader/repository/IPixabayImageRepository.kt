package com.example.i_larin.pixabayreader.repository

import rx.Observable

/**
 * Created by black-sony on 01.02.17.
 */
interface IPixabayImageRepository {

    fun getObserverDataChange(): Observable<PixabayImageRepository.PixabayImageResponce>
    fun loadMore(query: String?, aNew: Boolean)
}