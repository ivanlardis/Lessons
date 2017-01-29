package com.example.i_larin.pixabayreader.di.component

import com.example.i_larin.pixabayreader.di.model.BusinessLogicModule
import com.example.i_larin.pixabayreader.presentation.presenter.PixabayImagesPresenter
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by black-sony on 29.01.17.
 */

@Singleton
@Subcomponent(modules = arrayOf(BusinessLogicModule::class))
interface BusinessLogicComponent {

    fun inject(entry: PixabayImagesPresenter)
}