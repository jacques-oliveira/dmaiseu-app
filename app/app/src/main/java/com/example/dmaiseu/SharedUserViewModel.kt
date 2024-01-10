package com.example.dmaiseu

import android.content.SharedPreferences
import android.os.Build
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadDataProfile(sharedPrefs: SharedPreferences, user_name:TextView, user_rgp:TextView, user_state:TextView, userTranspDate:TextView, userHospistal:TextView,
                        userReturnDate:TextView, year_transp_time:TextView, month_transp_time:TextView, day_transp_time:TextView){
        val savedUserName = sharedPrefs.getString("user_name",null)
        val savedUser_RGP = sharedPrefs.getString("user_rgp",null)
        val savedState = sharedPrefs.getString("user_state",null)
        val savedTranspDate = sharedPrefs.getString("user_transp_date",null)
        val savedHospital =sharedPrefs.getString("user_hospital",null)
        val savedReturnDate = sharedPrefs.getString("user_return_date",null)
        var dateNow = Calendar.getInstance().time

        if(savedTranspDate != null){
            val transpFullTime = dateNow.time - (SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("Br")).parse(savedTranspDate)).time
            val year:Double =  transpFullTime/(3600.0*1000.0*24.0*365.0)
            val month:Double = (year - year.toInt())*12
            val day:Double = (month - month.toInt())*30

            if(year > 0){
                if(year >= 2){
                    year_transp_time.text = (year.toInt()).toString() + " anos"
                }else{
                    year_transp_time.text = (year.toInt()).toString() + " ano"
                }
            }else{
                year_transp_time.text = ""
            }
            if(month > 0){
                if(month >= 2){
                    month_transp_time.text = (month.toInt()).toString() + " meses"
                }else{
                    month_transp_time.text = (month.toInt()).toString() + " mês"
                }
            }else{
                month_transp_time.text = ""
            }
            if(day > 0){
                if(day >= 2){
                    day_transp_time.text = (day.toInt()).toString() + " dias"
                }else{
                    day_transp_time.text = (day.toInt()).toString() + " dia"
                }
            }else{
                day_transp_time.text = ""
            }
        }
        if(savedUserName != null){
            user_name?.text = savedUserName
        }
        if(savedUser_RGP != null){
            user_rgp?.text = savedUser_RGP
        }
        if(savedState != null){
            user_state?.text = savedState
        }
        if(savedTranspDate != null){
            userTranspDate?.text = savedTranspDate
        }
        if(savedHospital != null){
            userHospistal?.text = savedHospital
        }
        if(savedReturnDate != null){
            userReturnDate?.text = savedReturnDate
        }
    }
}