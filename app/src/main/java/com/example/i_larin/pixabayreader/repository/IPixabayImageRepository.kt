package com.example.i_larin.pixabayreader.repository

import com.example.i_larin.pixabayreader.model.app.PixabayImages
import com.example.i_larin.pixabayreader.repository.model.ResponceRepository
import rx.Observable

/**
 * Created by black-sony on 01.02.17.
 */
interface IPixabayImageRepository {

    fun getObserverDataChange(): Observable<ResponceRepository>

    fun loadMore(query: String?, aNew: Boolean)
}
