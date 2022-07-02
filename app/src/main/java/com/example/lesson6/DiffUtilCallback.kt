package com.example.lesson6

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback(
    private var oldItems: List<Pair<Data, Boolean>>,
    private var newItems: List<Pair<Data, Boolean>>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size
    override fun getNewListSize(): Int = newItems.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            =
        oldItems[oldItemPosition].first.header == newItems[newItemPosition].first.header
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int):
            Boolean =
        oldItems[oldItemPosition].first.header ==
                newItems[newItemPosition].first.header
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return Change(
            oldItem,
            newItem
        )
    }
}