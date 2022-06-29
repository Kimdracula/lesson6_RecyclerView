package com.example.lesson6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson6.databinding.FragmentRecyclerviewBinding

class RecyclerViewFragment : Fragment() {

    private var _binding: FragmentRecyclerviewBinding? = null
    private val binding get() = _binding!!
    lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var adapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerviewBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arrayListOf(
            Pair(Data(1, Data.TYPE_USUAL, "Заголовок", "Описание"), true),
            Pair(Data(1, Data.TYPE_USUAL, "Заголовок", "Описание"), true),
            Pair(Data(1, Data.TYPE_IMPORTANT, "Заголовок", "Описание"), true),
            Pair(Data(1, Data.TYPE_EXTRA, "Заголовок", "Описание"), true)
        )

        adapter = RecyclerAdapter(
            object : OnListItemClickListener {
                override fun onItemClick(data: Data) {
                    Toast.makeText(
                        requireContext(), "Кликнулось",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            data,
            object : OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }

        )
        binding.recycleList.adapter = adapter
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recycleList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun newInstance(): RecyclerViewFragment {
        return RecyclerViewFragment()
    }
}