package com.example.i_larin.pixabayreader

import android.app.Application
import com.example.i_larin.pixabayreader.di.DI

/**
 * Created by i_larin on 28.01.17.
 */
class PixabayReaderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DI.init(applicationContext)
    }
}