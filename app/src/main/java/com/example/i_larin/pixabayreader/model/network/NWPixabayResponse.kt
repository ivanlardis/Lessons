package com.example.i_larin.pixabayreader.model.network

/**
 * Created by i_larin on 28.01.17.
 */
data class NWPixabayResponse(val total: Int,
                             val hits: List<NWPixabayImage>? = null)
