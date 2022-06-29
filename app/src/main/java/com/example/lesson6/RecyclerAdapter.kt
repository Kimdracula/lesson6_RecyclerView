package com.example.lesson6

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson6.Data.Companion.TYPE_IMPORTANT
import com.example.lesson6.Data.Companion.TYPE_USUAL

class RecyclerAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: MutableList<Pair<Data, Boolean>>,
    private val dragListener: OnStartDragListener
) : RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    fun setItems(newItems: List<Pair<Data, Boolean>>) {
        val result = DiffUtil.calculateDiff(DiffUtilCallback(data, newItems))
        result.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newItems)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_USUAL -> UsualViewHolder(
                inflater.inflate(
                    R.layout.note_usual_item,
                    parent,
                    false
                ) as View
            )
            TYPE_IMPORTANT -> ImportantViewHolder(
                inflater.inflate(
                    R.layout.note_important_item,
                    parent,
                    false
                ) as View
            )
            else -> ExtraViewHolder(inflater.inflate(R.layout.note_extra_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

 /*   override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else {
            val combinedChange =
                createCombinedPayload(payloads as List<Change<Pair<Data, Boolean>>>)
            val oldData = combinedChange.oldData
            val newData = combinedChange.newData
            if (newData.first.header != oldData.first.header) {
                holder.itemView.findViewById<TextView>(R.id.textViewHeader).text =
                    newData.first.header
            }


        }
    }
    */

    override fun getItemCount(): Int = data.size


    override fun getItemViewType(position: Int): Int = data[position].first.type


    inner class UsualViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        @SuppressLint("ClickableViewAccessibility")
        override fun bind(data: Pair<Data, Boolean>) {

            setViews(itemView, data, layoutPosition)
            itemView.findViewById<ImageView>(R.id.dragHandleImageView)
                .setOnTouchListener { _, motionEvent ->
                    if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                        dragListener.onStartDrag(this)
                    }
                    false
                }
        }

        override fun onItemSelected() {
            TODO("Not yet implemented")
        }

        override fun onItemClear() {
            TODO("Not yet implemented")
        }
    }

    private fun setViews(itemView: View, data: Pair<Data, Boolean>, layoutPosition: Int) {
        itemView.findViewById<TextView>(R.id.textViewHeader).text = data.first.header

        itemView.findViewById<TextView>(R.id.textViewDescription).text = data.first.description

        itemView.findViewById<ImageView>(R.id.clearImageView).setOnClickListener {
            removeItem(layoutPosition)
        }
        itemView.findViewById<ImageView>(R.id.arrowDownImageView).setOnClickListener {
            moveDown(layoutPosition)
        }
        itemView.findViewById<ImageView>(R.id.arrowUpImageView).setOnClickListener {
            moveUp(layoutPosition)
        }

    }

    inner class ImportantViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        @SuppressLint("ClickableViewAccessibility")
        override fun bind(data: Pair<Data, Boolean>) {

            setViews(itemView, data, layoutPosition)
            itemView.findViewById<ImageView>(R.id.dragHandleImageView)
                .setOnTouchListener { _, motionEvent ->
                    if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                        dragListener.onStartDrag(this)
                    }
                    false
                }
        }
        override fun onItemSelected() {
            TODO("Not yet implemented")
        }

        override fun onItemClear() {
            TODO("Not yet implemented")
        }
    }

    inner class ExtraViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        @SuppressLint("ClickableViewAccessibility")
        override fun bind(data: Pair<Data, Boolean>) {

                setViews(itemView, data, layoutPosition)
                itemView.findViewById<ImageView>(R.id.dragHandleImageView)
                    .setOnTouchListener { _, motionEvent ->
                        if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                            dragListener.onStartDrag(this)
                        }
                        false
                    }
        }
        override fun onItemSelected() {
            TODO("Not yet implemented")
        }

        override fun onItemClear() {
            TODO("Not yet implemented")
        }
    }


    private fun toggleText(layoutPosition: Int) {
        data[layoutPosition] = data[layoutPosition].let {
            it.first to !it.second
        }
        notifyItemChanged(layoutPosition)
    }


    private fun moveUp(layoutPosition: Int) {
        layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
            data.removeAt(currentPosition).apply {
                data.add(currentPosition - 1, this)
            }
            notifyItemMoved(currentPosition, currentPosition - 1)
        }
    }

    private fun moveDown(layoutPosition: Int) {
        layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
            data.removeAt(currentPosition).apply {
                data.add(currentPosition + 1, this)
            }
            notifyItemMoved(currentPosition, currentPosition + 1)
        }
    }


    private fun removeItem(layoutPosition: Int) {
        data.removeAt(layoutPosition)
        notifyItemRemoved(layoutPosition)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(
                if (toPosition > fromPosition) toPosition - 1 else toPosition,
                this
            )
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }


}

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: Pair<Data, Boolean>)

}