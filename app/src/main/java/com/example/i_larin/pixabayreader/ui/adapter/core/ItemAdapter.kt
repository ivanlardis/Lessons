package com.example.black_sony.testrecyclerview.core

import android.support.v7.widget.RecyclerView
import com.example.black_sony.testrecyclerview.core.ItemView

/**
 * Created by black-sony on 26.02.17.
 */
interface ItemAdapter {

    fun getItem(position: Int): ItemView<*>
    fun getItemCount(): Int

    fun addHeader(headerItem: ItemView<*>)
    fun removeHeader(headerItem: ItemView<*>)

    fun addFooter(footerItem: ItemView<*>)
    fun removeFooter(footerItem: ItemView<*>)

    fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver)
}