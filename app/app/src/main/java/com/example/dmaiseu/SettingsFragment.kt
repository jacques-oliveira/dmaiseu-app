package com.example.dmaiseu

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.time.Year
import java.util.Calendar
import java.util.Locale

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
        val sharedPrefs: SharedPreferences = this.requireActivity().getSharedPreferences("SHARED_PREFS_USER", Context.MODE_PRIVATE)

         //loadData(sharedPrefs,user_name,userRGP,user_state,userTranspDate,userHospistal,userReturnDate)
        viewModel.loadData(sharedPrefs,user_name,userRGP,user_state,userTranspDate,userHospistal,userReturnDate)

        val calendar = Calendar.getInstance()

        val datePickerTranspDate = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            //updateDateValue(calendar,userTranspDate!!)
            viewModel.updateDateValue(calendar,userTranspDate!!)
        }

        val datePickerReturnDate = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            //updateDateValue(calendar,userReturnDate!!)
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
            //saveData(sharedPrefs,user_name,userRGP,user_state,userTranspDate,userHospistal,userReturnDate)
            if(viewModel.saveData(sharedPrefs,user_name,userRGP,user_state,userTranspDate,userHospistal,userReturnDate)){
                Toast.makeText(activity, "Dados Salvos com sucesso!",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(activity, "Preencha seus dados!",Toast.LENGTH_LONG).show()
            }
            viewModel.saveData(sharedPrefs,user_name,userRGP,user_state,userTranspDate,userHospistal,userReturnDate)
        }
    }
/*    private fun updateDateValue(calendar: Calendar,txtView: TextView) {
        val format = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(format, Locale.forLanguageTag("BR"))
        txtView?.setText(sdf.format(calendar.time))
    }*/

 /*   private fun saveData(sharedPrefs:SharedPreferences,user_name:EditText,userRGP:EditText,user_state:EditText, userTranspDate:TextView,userHospistal:EditText ,userReturnDate:TextView){

        val editor:SharedPreferences.Editor = sharedPrefs.edit()
        if(userRGP?.text != null && user_name?.text != null && user_state?.text != null
            && userTranspDate?.text != null){
            editor.apply {
                putString("user_rgp", userRGP?.text.toString())
                putString("user_name",user_name?.text.toString())
                putString("user_state",user_state?.text.toString())
                putString("user_transp_date",userTranspDate?.text.toString())
                putString("user_hospital",userHospistal?.text.toString())
                putString("user_return_date",userReturnDate?.text.toString())

            }.apply()
            Toast.makeText(activity, "Dados Salvos com sucesso!",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(activity, "Preencha seus dados!",Toast.LENGTH_LONG).show()
        }
    }*/
/*    private fun loadData(sharedPrefs: SharedPreferences, user_name:EditText,userRGP:EditText,user_state:EditText, userTranspDate:TextView,userHospistal:EditText ,userReturnDate:TextView){

        val savedUserName = sharedPrefs.getString("user_name",null)
        val savedUser_RGP = sharedPrefs.getString("user_rgp",null)
        val savedState = sharedPrefs.getString("user_state",null)
        val savedTranspDate = sharedPrefs.getString("user_transp_date",null)
        val savedHospital =sharedPrefs.getString("user_hospital",null)
        val savedReturnDate = sharedPrefs.getString("user_return_date",null)

        if(savedUserName != null){
            user_name?.setText(savedUserName)
        }
        if(savedUser_RGP != null){
            userRGP?.setText(savedUser_RGP)
        }
        if(savedState != null){
            user_state?.setText(savedState)
        }
        if(savedTranspDate != null){
            userTranspDate?.text = savedTranspDate
        }
        if(savedHospital != null){
            userHospistal?.setText(savedHospital)
        }
        if(savedReturnDate != null){
            userReturnDate?.text = savedReturnDate
        }
    }*/
}