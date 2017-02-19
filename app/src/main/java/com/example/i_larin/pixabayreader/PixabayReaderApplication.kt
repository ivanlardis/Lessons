package com.example.i_larin.pixabayreader

import android.app.Application
import com.example.i_larin.pixabayreader.di.DI
import timber.log.Timber

/**
 * Created by i_larin on 28.01.17.
 */
class PixabayReaderApplication : Application() {
    companion object {
        fun isDemoContent() = !BuildConfig.DEMO_VERSION_CONTENTS
    }

    override fun onCreate() {
        super.onCreate()
        DI.init(applicationContext)

        if (BuildConfig.WITH_LOGS) {
            Timber.plant(Timber.DebugTree())
        }
    }
}


