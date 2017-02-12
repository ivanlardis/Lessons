package com.example.i_larin.pixabayreader.model.converter

import android.util.Log
import com.example.i_larin.pixabayreader.model.app.PixabayImage
import com.example.i_larin.pixabayreader.model.network.NWPixabayImage
import timber.log.Timber

/**
 * Created by i_larin on 28.01.17.
 */
class PixabayImageConverter {
    companion object {
        val DEFAULT_WEB_URL = "https://pixabay.com/get/ee32b90c29f11c2ad65a5854e24f4e90e677e4c818b5194492f1c57ca2ef_640.jpg"
        val DEFAULT_PREVIEW_URL = "https://pixabay.com/get/ee32b90c29f11c2ad65a5854e24f4e90e677e4c818b5194492f1c57ca2ef_640.jpg"
        fun fromNetwork(nWPixabayImage: NWPixabayImage): PixabayImage {
            return PixabayImage(nWPixabayImage.id, getOrDie(nWPixabayImage.webformatURL, DEFAULT_WEB_URL),
                    getOrDie(nWPixabayImage.previewURL, DEFAULT_PREVIEW_URL),
                    getOrDie(nWPixabayImage.tags, "Нет тегов"))

        }

        fun getOrDie(name: String?, nameNull: String): String {

            if (name != null) return name
            else {
               Timber.e(  "PixabayImageConverter неполнота данных")
                return nameNull
            }
        }
    }

}