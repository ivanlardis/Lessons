package com.example.i_larin.pixabayreader.di.model

import com.example.i_larin.pixabayreader.network.ApiFactoty
import com.example.i_larin.pixabayreader.network.PixabayImageApi
import com.example.i_larin.pixabayreader.repository.PixabayImageRepository
import com.f2prateek.rx.preferences.RxSharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by black-sony on 29.01.17.
 */

@Module
class BusinessLogicModule {

    @Provides
    @Singleton
    fun providePixabayImageRepository(pixabayImageApi: PixabayImageApi,rxSharedPreferences: RxSharedPreferences): PixabayImageRepository {
        return PixabayImageRepository(pixabayImageApi,rxSharedPreferences)
    }

}