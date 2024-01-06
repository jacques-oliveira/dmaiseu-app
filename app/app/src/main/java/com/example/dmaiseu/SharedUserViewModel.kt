package com.example.dmaiseu

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SharedUserViewModel :ViewModel(){

    fun saveData(sharedPrefs:SharedPreferences,user_name: EditText, userRGP: EditText, user_state: EditText, userTranspDate: TextView, userHospistal: EditText, userReturnDate: TextView):Boolean{
        val editor: SharedPreferences.Editor = sharedPrefs.edit()
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
            return true
        }
        return false
    }

    fun loadData(sharedPrefs: SharedPreferences, user_name:EditText,userRGP:EditText,user_state:EditText, userTranspDate:TextView,userHospistal:EditText ,userReturnDate:TextView){
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
    }

    fun updateDateValue(calendar: Calendar, txtView: TextView) {
        val format = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(format, Locale.forLanguageTag("BR"))
        txtView?.setText(sdf.format(calendar.time))
    }
}