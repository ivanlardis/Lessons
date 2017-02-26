package com.example.black_sony.testrecyclerview.core

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * Created by black-sony on 26.02.17.
 */
class GroopAdapter () : RecyclerView.Adapter<GroopAdapter.ViewHolder>() {

    var adapterDataObserver: AdapterDataObserver

    var headerItems: ArrayList<ItemView<*>> = ArrayList()
    var footerItems: ArrayList<ItemView<*>> = ArrayList()

    private var data: HashMap<GroupTitleItemView<*>, ItemAdapter> = HashMap()

    init {
        adapterDataObserver = AdapterDataObserver()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(parent?.getContext())
                .inflate(viewType, parent, false);
        return ViewHolder(v)
    }


    fun clearData() = data.clear()


    fun addGroopData(groupItemView: GroupTitleItemView<*>, itemAdapter: ItemAdapter) {

        groupItemView.registerAdapterDataObserver(adapterDataObserver)
        itemAdapter.registerAdapterDataObserver(adapterDataObserver)

        data.put(groupItemView, itemAdapter)

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            getItem(position).bind(holder.itemView)

    override fun getItemViewType(position: Int) = getItem(position).getLayoutId()


    fun getItem(position: Int): ItemView<*> {
        var groupPosition = 0
        var itemPosition = position
        if (itemPosition < headerItems.size) {
            return headerItems[itemPosition]
        } else {
            itemPosition -= headerItems.size
        }
        for ((group, adapter) in data.entries) {
            when {
                itemPosition >= getGroupItemCount(group) -> {
                    groupPosition++
                    itemPosition -= getGroupItemCount(group)
                }
                itemPosition == 0 -> {
                    return getGroup(groupPosition)
                }
                itemPosition < getGroupItemCount(group) -> {
                    return adapter.getItem(itemPosition - 1)
                }
            }
        }
        return footerItems[itemPosition]
    }


    private fun getGroup(position: Int): ItemView<*> = data.keys.toList()[position]

    private fun getGroupItemCount(group: GroupTitleItemView<*>): Int {
        val nestedItemCount = data[group]?.getItemCount() ?: 0
        val hasNoGroup = group.hideOnEmptyGroup() && nestedItemCount == 0

        var i = when {
            hasNoGroup -> 0
            group.expand -> nestedItemCount + 1
            else -> 1
        }

        return i
    }

    override fun getItemCount(): Int = headerItems.size + data.keys.sumBy { getGroupItemCount(it) } + footerItems.size


    inner class AdapterDataObserver() : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            notifyDataSetChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            notifyDataSetChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            notifyDataSetChanged()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            notifyDataSetChanged()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            notifyDataSetChanged()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            notifyDataSetChanged()
        }
    }

}

