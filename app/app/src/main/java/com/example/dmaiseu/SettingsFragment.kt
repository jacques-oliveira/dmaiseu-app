package com.example.dmaiseu

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar

class SettingsFragment : Fragment(){
    private lateinit var viewModel: SharedUserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity()).get(SharedUserViewModel::class.java)
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSave = view?.findViewById<Button>(R.id.btn_save)
        val btnTranspDate = view?.findViewById<ImageView>(R.id.transp_date_picker)
        val btnReturnDate = view?.findViewById<ImageView>(R.id.reuturn_date_picker)
        val userTranspDate:TextView = view?.findViewById<TextView>(R.id.user_transp_date_value) as TextView
        val userReturnDate:TextView = view?.findViewById<TextView>(R.id.user_return_date_value) as TextView
        val userRGP:EditText  = view?.findViewById<EditText>(R.id.user_rgp_value) as EditText
        val user_name:EditText = view?.findViewById<EditText>(R.id.user_name_value) as EditText
        val user_state:EditText  = view?.findViewById<EditText>(R.id.user_state_value) as EditText
        val userHospistal:EditText  =view?.findViewById<EditText>(R.id.hospital_value) as EditText
        val bloodTypeSpinner : Spinner = view?.findViewById<Spinner>(R.id.blood_options) as Spinner
        val sharedPrefs: SharedPreferences = this.requireActivity().getSharedPreferences("SHARED_PREFS_USER", Context.MODE_PRIVATE)
        val bloodSpinner = view.findViewById<Spinner>(R.id.blood_options)

        val bloodAdapter = ArrayAdapter(requireActivity(),
                            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,viewModel.bloodOptions)

        bloodSpinner.adapter = bloodAdapter

        viewModel.loadDataSettings(sharedPrefs,user_name,userRGP,user_state,userTranspDate,userHospistal,userReturnDate, bloodSpinner)

        val calendar = Calendar.getInstance()

        val datePickerTranspDate = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            viewModel.updateDateValue(calendar,userTranspDate!!)
        }

        val datePickerReturnDate = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            viewModel.updateDateValue(calendar,userReturnDate!!)
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

        btnSave!!.setOnClickListener {

            val bloodPosition = bloodAdapter.getPosition(bloodSpinner.selectedItem.toString())
            if(viewModel.saveData(sharedPrefs,user_name.text.toString(),userRGP.text.toString(),user_state.text.toString(),
                    userTranspDate.text.toString(),userHospistal.text.toString(),userReturnDate.text.toString(),bloodPosition) ){
                    Toast.makeText(activity, "Dados Salvos com sucesso!",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(activity, "Preencha seus dados!",Toast.LENGTH_LONG).show()
            }
        }
    }
}