package com.example.i_larin.pixabayreader.repository

import com.example.i_larin.pixabayreader.model.app.PixabayImages
import rx.Observable

/**
 * Created by black-sony on 01.02.17.
 */
interface IPixabayImageRepository {

    fun getObserverDataChange(): Observable<PixabayImageResponce>

    fun loadMore(query: String?, aNew: Boolean)

    enum class State {
        ERROR,
        NEXT_ITEMS,
        NEW_ITEMS,
        END_ITEMS;

        var description: String = ""
    }

    data class PixabayImageResponce(val state: State, val pixabayImagesVisual: PixabayImages);

}
