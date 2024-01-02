package com.example.dmaiseu

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.time.Year
import java.util.Calendar
import java.util.Locale

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSave = view?.findViewById<Button>(R.id.btn_save)
        val btnTranspDate:ImageView = view?.findViewById<ImageView>(R.id.transp_date_picker) as ImageView
        val btnReturnDate:ImageView = view?.findViewById<ImageView>(R.id.reuturn_date_picker) as ImageView
        val transpDateValue = view?.findViewById<TextView>(R.id.user_transp_date_value)
        val returnDateValue = view?.findViewById<TextView>(R.id.user_return_date_value)

        val calendar = Calendar.getInstance()

        val datePickerTranspDate = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateDateValue(calendar,transpDateValue!!)
        }

        val datePickerReturnDate = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateDateValue(calendar,returnDateValue!!)
        }

        btnTranspDate?.setOnClickListener {

            DatePickerDialog(requireContext(),datePickerTranspDate,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnReturnDate?.setOnClickListener{

            DatePickerDialog(requireContext(),datePickerReturnDate,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
    private fun updateDateValue(calendar: Calendar,txtView: TextView) {
        val format = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(format, Locale.forLanguageTag("BR"))
        txtView?.setText(sdf.format(calendar.time))
    }
}