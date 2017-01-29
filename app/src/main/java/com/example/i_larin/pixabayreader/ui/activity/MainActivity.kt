package com.example.i_larin.pixabayreader.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.i_larin.pixabayreader.R
import com.example.i_larin.pixabayreader.ui.fragment.PixabayImagesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, PixabayImagesFragment.newInstance(), "TAG")
                    .commit()
        }
    }
}
