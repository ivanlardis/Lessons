package com.example.i_larin.pixabayreader.repository.model

import com.example.i_larin.pixabayreader.model.app.PixabayImages

/**
 * Created by black-sony on 26.02.17.
 */
data class ResponceRepository(val state: StateRepository, val pixabayImagesVisual: List<PixabayImages>?);
