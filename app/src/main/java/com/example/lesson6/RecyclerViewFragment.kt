package com.example.lesson6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson6.databinding.FragmentRecyclerviewBinding

class RecyclerViewFragment : Fragment() {

    private var _binding: FragmentRecyclerviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RecyclerAdapter
    private var data :MutableList<Pair<Data,Boolean>> = arrayListOf()
   private var savedData: Data? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerviewBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
if(data.size==0) {
    data.add(Pair(Data(Data.TYPE_USUAL, "Заголовок 1", "Описание 1"), true))
    data.add(Pair(Data(Data.TYPE_EXTRA, "Заголовок 1", "Описание 1"), true))
    data.add(Pair(Data(Data.TYPE_IMPORTANT, "Заголовок 1", "Описание 1"), true))
    data.add(Pair(Data(Data.TYPE_USUAL, "Заголовок 1", "Описание 1"), true))
    data.add(Pair(Data(Data.TYPE_EXTRA, "Заголовок 1", "Описание 1"), true))
    data.add(Pair(Data(Data.TYPE_IMPORTANT, "Заголовок 1", "Описание 1"), true))
}


        setAddNoteButtonBehavior()
        setAdaptersAndHelpers(data as ArrayList<Pair<Data, Boolean>>)
        if (arguments != null) {
            savedData = requireArguments().getParcelable(KEY_PARCEL)
            data.add(Pair(savedData,false) as Pair<Data, Boolean>)
            adapter.notifyItemInserted(data.size-1)
        }
    }

    private fun setAdaptersAndHelpers(data: ArrayList<Pair<Data, Boolean>>) {
        adapter = RecyclerAdapter(
            object : OnListItemClickListener {
                override fun onItemClick(data: Data) {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, DetailsFragment.newInstance(data))
                        .addToBackStack("").commit()
                }
            },
            data,
        )
        binding.recycleList.adapter = adapter

    }

    private fun setAddNoteButtonBehavior() {
        binding.buttonAddNote.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, DetailsFragment.newInstance())
                .addToBackStack("").commit()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun newInstance(): RecyclerViewFragment {
        return RecyclerViewFragment()
    }

}