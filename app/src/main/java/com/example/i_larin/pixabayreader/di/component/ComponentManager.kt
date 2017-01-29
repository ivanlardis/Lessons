package com.example.i_larin.pixabayreader.di.component

import android.content.Context
import com.example.i_larin.pixabayreader.di.model.BusinessLogicModule

import com.example.i_larin.pixabayreader.di.model.ModelModule


/**
 * Created by black-sony on 29.01.17.
 */
class ComponentManager(val context: Context) {
    val internalAppComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .modelModule(ModelModule(context))

                .build()
    }
    val internalBusinessLogicComponent by lazy {
        internalAppComponent.plus(BusinessLogicModule())
    }
    fun businessLogicComponent() = internalBusinessLogicComponent
    fun appComponent() = internalAppComponent
}