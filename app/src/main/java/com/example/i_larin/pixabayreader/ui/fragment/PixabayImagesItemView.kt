package com.example.i_larin.pixabayreader.ui.adapter.view

import android.view.View
import com.example.black_sony.testrecyclerview.core.ItemView
import com.example.i_larin.pixabayreader.R
import com.example.i_larin.pixabayreader.model.app.PixabayImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pixabay_images_adapte_item.view.*

/**
 * Created by black-sony on 26.02.17.
 */
class PixabayImagesItemView(value: PixabayImage) : ItemView<PixabayImage>(value, {}) {
    override fun getLayoutId() = R.layout.pixabay_images_adapte_item

    override fun bind(view: View) {
        super.bind(view)
        with(view)
        {
            Picasso.with(context)
                    .load(value.previewURL)
                    .into(imageViewItem)
            textViewItem.text = value.tags

        }
    }
}