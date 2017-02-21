package com.example.i_larin.pixabayreader.ui.fragment

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.i_larin.pixabayreader.R
import com.example.i_larin.pixabayreader.model.app.PixabayImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pixabay_images_adapte_item.view.*
import java.util.*

/**
 * Created by i_larin on 28.01.17.
 */
class PixabayImagesAdapter(private val fragment: Fragment) : RecyclerView.Adapter<PixabayImagesAdapter.ViewHolder>() {
    private val dataset: MutableList<PixabayImage> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context)
                .inflate(R.layout.pixabay_images_adapte_item, parent, false)
        return ViewHolder(item)
    }

    // TODO use property syntax
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO use holder.itemView.context, remove fragment from constructor
        Picasso.with(fragment.activity)
                .load(dataset.get(position).previewURL)
                .into(holder.mImageView)
        holder.mTextViewName.setText(dataset.get(position).tags)

    }

    fun addPixabayImage(performer: List<PixabayImage>) {
        dataset.addAll(performer.subList(dataset.size, performer.size - 1))
        notifyItemInserted(dataset.size - 1)
    }

    fun updateIsNullPixabayImage(performer: List<PixabayImage>) {
        if (dataset.size == 0) updatePixabayImage(performer)
        // TODO if (dataset.isEmpty()) updatePixabayImage(performer)
    }

    fun updatePixabayImage(performer: List<PixabayImage>) {
        dataset.clear()
        dataset.addAll(performer)
        notifyDataSetChanged()
    }


    override fun getItemCount() = dataset.size


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var mImageView: ImageView =v.imageViewItem
        var mTextViewName: TextView= v.textViewItem
    }

}