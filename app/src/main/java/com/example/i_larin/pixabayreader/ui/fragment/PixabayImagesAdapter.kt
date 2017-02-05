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
import java.util.*

/**
 * Created by i_larin on 28.01.17.
 */
class PixabayImagesAdapter(private val fragment: Fragment) : RecyclerView.Adapter<PixabayImagesAdapter.ViewHolder>() {
    private val dataset: MutableList<PixabayImage>

    init {
        this.dataset = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context)
                .inflate(R.layout.pixabay_images_adapte_item, parent, false)
        val viewHolder = ViewHolder(item)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.with(fragment.activity)
                .load(dataset.get(position).previewURL)
//                .placeholder(R.drawable.placeholder)
                .into(holder.mImageView)
        holder.mTextViewName.setText(dataset.get(position).tags)

    }

    fun addPixabayImage(performer: List<PixabayImage>) {
        dataset.addAll(performer.subList(dataset.size , performer.size - 1))
        notifyItemInserted(dataset.size - 1)
    }

    fun updateIsNullPixabayImage(performer: List<PixabayImage>) {
        if (dataset.size == 0) updatePixabayImage(performer)
    }

    fun updatePixabayImage(performer: List<PixabayImage>) {
        dataset.clear()
        dataset.addAll(performer)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return dataset.size

    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var mImageView: ImageView
        var mTextViewName: TextView


        init {
            mImageView = v.findViewById(R.id.imageViewItem) as ImageView
            mTextViewName = v.findViewById(R.id.textViewItem) as TextView

        }
    }

}