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

    fun showData(pixabayImage: List<PixabayImages>?)


    @StateStrategyType(value = SkipStrategy::class)
    fun notifyUser(errorName: String)

    fun stopRefresh()

}

