package com.example.i_larin.pixabayreader.presentation.view

import com.example.i_larin.pixabayreader.model.app.PixabayImage
import com.example.i_larin.pixabayreader.model.app.PixabayImagesVisual

/**
 * Created by i_larin on 28.01.17.
 */
interface PixabayImagesView {

    fun showData(pixabayImage: List<PixabayImage>)

    fun showMoreData(pixabayImage: List<PixabayImage>)

    fun showLoadingMoreProgress(show: Boolean)

    fun showPullRefreshEnabled(show: Boolean)

    fun setTitleActionBar(title: String)

    fun notifyUser(errorName: String)
}