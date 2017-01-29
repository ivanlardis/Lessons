package com.example.i_larin.pixabayreader.di.component

import com.example.i_larin.pixabayreader.di.model.BusinessLogicModule
import com.example.i_larin.pixabayreader.di.model.ModelModule
import com.example.i_larin.pixabayreader.repository.PixabayImageRepository
import dagger.Component
import javax.inject.Singleton

/**
 * Created by black-sony on 29.01.17.
 */


@Singleton
@Component(modules = arrayOf(ModelModule::class))
interface AppComponent {

    fun plus(businessLogicModule: BusinessLogicModule): BusinessLogicComponent

    fun inject(entry: PixabayImageRepository)
}