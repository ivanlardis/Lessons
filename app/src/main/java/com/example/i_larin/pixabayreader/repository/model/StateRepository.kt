package com.example.i_larin.pixabayreader.repository.model

/**
 * Created by black-sony on 26.02.17.
 */
enum class StateRepository {
    ERROR,
    NEXT_ITEMS,
    NEW_ITEMS,
    END_ITEMS;

    var description: String = ""
}