package com.example.lesson6

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
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
) : RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter,Filterable {


    var filterList = ArrayList<Pair<Data, Boolean>>()
    init {
        filterList = data as ArrayList<Pair<Data, Boolean>>
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
        holder.bind(filterList[position])
    }
  /*  override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else {
            val combinedChange = createCombinedPayload(payloads as List<Change<Pair<Data, Boolean>>>)
            val oldData = combinedChange.oldData
            val newData = combinedChange.newData
            if (newData.first.header != oldData.first.header) {
              holder.bind(filterList[position])
                holder.itemView.findViewById<TextView>(R.id.textViewHeaderImportant)
            }
        }
    }

   *////////// У МЕНЯ ТОЧЕЧНО НИЧЕГО НЕ МЕНЯЕТСЯ- НЕ РЕАЛИЗОВЫВАЛ. НО КАК РАБОТАЕТ ПОНЯЛ.


    override fun getItemCount(): Int = filterList.size


    override fun getItemViewType(position: Int): Int = filterList[position].first.type


    inner class UsualViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        @SuppressLint("ClickableViewAccessibility")
        override fun bind(data: Pair<Data, Boolean>) {

            with(itemView) {

                itemView.setOnClickListener { onListItemClickListener.onItemClick(data.first) }

                findViewById<TextView>(R.id.textViewHeaderUsual).text = data.first.header

                findViewById<TextView>(R.id.textViewDescriptionUsual).text = data.first.description

                findViewById<ImageView>(R.id.clearImageViewUsual).setOnClickListener {
                    removeItem(layoutPosition)
                }
                findViewById<ImageView>(R.id.arrowDownImageViewUsual).setOnClickListener {
                    moveDown(layoutPosition)
                }
                findViewById<ImageView>(R.id.arrowUpImageViewUsual).setOnClickListener {
                    moveUp(layoutPosition)
                }
            }

            itemView.findViewById<ImageView>(R.id.dragHandleImageViewUsual)
                .setOnTouchListener { _, motionEvent ->
                    if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                        dragListener.onStartDrag(this)
                    }
                    false
                }
        }

        override fun onItemSelected() {}

        override fun onItemClear() {}
    }


    inner class ImportantViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        @SuppressLint("ClickableViewAccessibility")
        override fun bind(data: Pair<Data, Boolean>) {

            with(itemView) {

                itemView.setOnClickListener { onListItemClickListener.onItemClick(data.first) }

                findViewById<TextView>(R.id.textViewHeaderImportant).text = data.first.header

                findViewById<TextView>(R.id.textViewDescriptionImportant).text = data.first.description

                findViewById<ImageView>(R.id.clearImageViewImportant).setOnClickListener {
                    removeItem(layoutPosition)
                }
                findViewById<ImageView>(R.id.arrowDownImageViewImportant).setOnClickListener {
                    moveDown(layoutPosition)
                }
                findViewById<ImageView>(R.id.arrowUpImageViewImportant).setOnClickListener {
                    moveUp(layoutPosition)
                }
            }
            itemView.findViewById<ImageView>(R.id.dragHandleImageViewImportant)
                .setOnTouchListener { _, motionEvent ->
                    if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                        dragListener.onStartDrag(this)
                    }
                    false
                }
        }

        override fun onItemSelected() {}
        override fun onItemClear() {}
    }

    inner class ExtraViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        @SuppressLint("ClickableViewAccessibility")
        override fun bind(data: Pair<Data, Boolean>) {

            with(itemView) {

                itemView.setOnClickListener { onListItemClickListener.onItemClick(data.first) }

                findViewById<TextView>(R.id.textViewHeaderExtra).text = data.first.header

                findViewById<TextView>(R.id.textViewDescriptionExtra).text = data.first.description

                findViewById<ImageView>(R.id.clearImageViewExtra).setOnClickListener {
                    removeItem(layoutPosition)
                }
                findViewById<ImageView>(R.id.arrowDownImageViewExtra).setOnClickListener {
                    moveDown(layoutPosition)
                }
                findViewById<ImageView>(R.id.arrowUpImageViewExtra).setOnClickListener {
                    moveUp(layoutPosition)
                }
            }


            itemView.findViewById<ImageView>(R.id.dragHandleImageViewExtra)
                .setOnTouchListener { _, motionEvent ->
                    if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                        dragListener.onStartDrag(this)
                    }
                    false
                }
        }

        override fun onItemSelected() {}
        override fun onItemClear() {}
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

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) filterList = data as ArrayList<Pair<Data, Boolean>> else {
                    val filteredList = ArrayList<Pair<Data, Boolean>>()
                    data
                        .filter {
                            (it.first.header!!.contains(constraint!!)) or
                                    (it.first.description!!.contains(constraint))

                        }
                        .forEach { filteredList.add(it) }
                    filterList = filteredList

                }
                return FilterResults().apply { values = filterList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Pair<Data, Boolean>>
                setItems(results!!.values as ArrayList<Pair<Data, Boolean>>)
            }
        }

    }
    fun setItems(newItems: List<Pair<Data, Boolean>>) {
        val result = DiffUtil.calculateDiff(DiffUtilCallback(data, newItems))
        result.dispatchUpdatesTo(this)
    }
}

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: Pair<Data, Boolean>)

}