package com.example.i_larin.pixabayreader.di

import android.content.Context
import com.example.i_larin.pixabayreader.di.component.ComponentManager

/**
 * Created by black-sony on 29.01.17.
 */
object DI {
    private lateinit var componentManager: ComponentManager
    fun init(contet:Context)
    {
        componentManager= ComponentManager(contet)
            }
    fun componentManager(): ComponentManager = componentManager

}