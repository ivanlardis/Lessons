package com.example.black_sony.testrecyclerview.view

import android.view.View
import android.widget.TextView
import com.example.black_sony.testrecyclerview.core.GroupTitleItemView
import com.example.black_sony.testrecyclerview.core.ItemView
import com.example.i_larin.pixabayreader.R

/**
 * Created by black-sony on 26.02.17.
 */
class PihabayImagesTitleItemView(value: String) : GroupTitleItemView<String>(value, {}) {
    override fun hideOnEmptyGroup(): Boolean  =false

    override fun getLayoutId(): Int {

        return R.layout.pixabay_images_groop_item; }


    override fun bind(view: View) {
        super.bind(view)

        with(view)
        {
            (findViewById(R.id.info_text) as TextView).text=value

        }


    }
}