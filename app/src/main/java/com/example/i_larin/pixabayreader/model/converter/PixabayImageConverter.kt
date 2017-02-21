package com.example.i_larin.pixabayreader.model.converter

import com.example.i_larin.pixabayreader.model.app.PixabayImage
import com.example.i_larin.pixabayreader.model.network.NWPixabayImage

/**
 * Created by i_larin on 28.01.17.
 */
class PixabayImageConverter {
    companion object {
        val DEFAULT_WEB_URL = "https://pixabay.com/get/ee32b90c29f11c2ad65a5854e24f4e90e677e4c818b5194492f1c57ca2ef_640.jpg"
        val DEFAULT_PREVIEW_URL = "https://pixabay.com/get/ee32b90c29f11c2ad65a5854e24f4e90e677e4c818b5194492f1c57ca2ef_640.jpg"

        fun fromNetwork(nWPixabayImage: NWPixabayImage) =
                PixabayImage(
                        nWPixabayImage.id,
                        getOrDie(nWPixabayImage.webformatURL, DEFAULT_WEB_URL),
                        getOrDie(nWPixabayImage.previewURL, DEFAULT_PREVIEW_URL),
                        getOrDie(nWPixabayImage.tags, "Нет тегов"))

        fun getOrDie(name: String?, nameNull: String) = name?.let { it } ?: nameNull
        fun getOrDie_(name: String?, nameNull: String) = name ?: nameNull

    }

}