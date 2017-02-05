package com.example.i_larin.pixabayreader.presentation.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.i_larin.pixabayreader.model.app.PixabayImage
import com.example.i_larin.pixabayreader.model.app.PixabayImages

/**
 * Created by i_larin on 28.01.17.
 */

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface PixabayImagesView : MvpView {

    fun showData(state: State, pixabayImage: List<PixabayImage>)

    fun showLoadingMoreProgress(show: Boolean)

    fun showPullRefreshEnabled(show: Boolean)

    fun setTitleActionBar(title: String)
    @StateStrategyType(value = SkipStrategy::class)
    fun notifyUser(errorName: String)

    enum class State {
        SHOW,
        SHOW_MORE,
        SHOW_IS_DATA_NULL

    }
}

