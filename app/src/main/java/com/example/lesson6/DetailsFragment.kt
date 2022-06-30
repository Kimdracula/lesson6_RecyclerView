package com.example.lesson6

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lesson6.databinding.FragmentDetailsBinding
import java.text.SimpleDateFormat
import java.util.*


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var data: Data? = null
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var cal: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            data = requireArguments().getParcelable(KEY_PARCEL)
            populateViews()
        }
        else  setDefaultDate()
        cal = Calendar.getInstance()
        initPickerDialog()
    }

    private fun setDefaultDate() {
     binding.textViewDate.text = SimpleDateFormat("dd.MMM.yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
    }

    private fun populateViews() {
        binding.editTextHeader.setText(data?.header ?: "" )
        binding.editTextDescription.setText(data?.description ?: "")
       binding .textViewDate.text = data?.date?.let { SimpleDateFormat("dd.MMM.yyyy", Locale.getDefault()).format(it) }
    }
    @SuppressLint("SetTextI18n")
    private var dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->

        binding.textViewDate.text = "$day.$month.$year"
    }

    private fun initPickerDialog() {
        datePickerDialog = DatePickerDialog(
            requireContext(), android.R.style.Theme_Material_Dialog_NoActionBar, dateSetListener,
            cal[Calendar.YEAR],
            cal[Calendar.MONTH],
            cal[Calendar.DAY_OF_MONTH]
        )
        cal[Calendar.HOUR_OF_DAY]
    }

    private val dateFromDatePicker: Date
        get() {
            cal[Calendar.YEAR] = datePickerDialog.datePicker.year
            cal[Calendar.MONTH] = datePickerDialog.datePicker.month
            cal[Calendar.DAY_OF_MONTH] = datePickerDialog.datePicker.dayOfMonth
            return cal.time
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): DetailsFragment {
            return DetailsFragment()
        }


        fun newInstance(data: Data): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable(KEY_PARCEL, data)
            fragment.arguments = args
            return fragment
    }
}

}
