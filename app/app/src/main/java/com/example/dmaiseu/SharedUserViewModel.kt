package com.example.dmaiseu

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SharedUserViewModel :ViewModel(){
    val bloodOptions = arrayOf("O+","O-","A+","A-","B+","B-","AB+","AB-")
    val internalFilePath:String = "/storage/emulated/0/Android/data/com.BDgames.DmaisEu/files/DCIM"
    val ImageFolderName:String = "DmaisEuFolder"
    var userImageName:String? = null
    fun saveData(sharedPrefs:SharedPreferences,user_name: String, userRGP: String, user_state: String, userTranspDate: String,
                 userHospistal: String, userReturnDate: String, userBlood:Int):Boolean{
        val editor: SharedPreferences.Editor = sharedPrefs.edit()
        if(userRGP != ""  && user_name != "" && user_state != ""
            && userTranspDate != ""){
            editor.apply {
                putString("user_rgp", userRGP?.toString())
                putString("user_name",user_name?.toString())
                putString("user_state",user_state?.toString())
                putString("user_transp_date",userTranspDate?.toString())
                putString("user_hospital",userHospistal?.toString())
                putString("user_return_date",userReturnDate?.toString())
                putInt("user_blood",userBlood)
            }.apply()
            return true
        }
        return false
    }

    fun loadDataSettings(sharedPrefs: SharedPreferences, user_name:EditText,userRGP:EditText,user_state:EditText, userTranspDate:TextView,
                 userHospistal:EditText ,userReturnDate:TextView,userBloodSpinner:Spinner){
        val savedUserName = sharedPrefs.getString("user_name",null)
        val savedUser_RGP = sharedPrefs.getString("user_rgp",null)
        val savedState = sharedPrefs.getString("user_state",null)
        val savedTranspDate = sharedPrefs.getString("user_transp_date",null)
        val savedHospital =sharedPrefs.getString("user_hospital",null)
        val savedReturnDate = sharedPrefs.getString("user_return_date",null)
        val savedBlood:Int = sharedPrefs.getInt("user_blood",0)

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
        if(savedBlood != null){
            userBloodSpinner.setSelection(savedBlood)
        }
    }

    fun updateDateValue(calendar: Calendar, txtView: TextView) {
        val format = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(format, Locale.forLanguageTag("BR"))
        txtView?.setText(sdf.format(calendar.time))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadDataProfile(sharedPrefs: SharedPreferences, user_name:TextView, user_rgp:TextView, user_state:TextView, userTranspDate:TextView, userHospistal:TextView,
                        userReturnDate:TextView, stringTranspTime:TextView, user_blood:TextView){
        val savedUserName = sharedPrefs.getString("user_name",null)
        val savedUser_RGP = sharedPrefs.getString("user_rgp",null)
        val savedState = sharedPrefs.getString("user_state",null)
        val savedTranspDate = sharedPrefs.getString("user_transp_date",null)
        val savedHospital =sharedPrefs.getString("user_hospital",null)
        val savedReturnDate = sharedPrefs.getString("user_return_date",null)
        val savedUserBloodPosition = sharedPrefs.getInt("user_blood",0)
        var dateNow = Calendar.getInstance().time
        val savedUserImageName =sharedPrefs.getString("user_image_name",null)

        if(savedTranspDate != null){
            val transpFullTime = dateNow.time - (SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("Br")).parse(savedTranspDate)).time
            val year:Double =  transpFullTime/(3600.0*1000.0*24.0*365.0)
            val month:Double = (year - year.toInt())*12
            val day:Double = (month - month.toInt())*30
            //var transpTimeText:String? = null
            var stringYearTime:String? = null
            var stringMonthTime:String? = null
            var stringDayTime:String? = null
            var stringFullTime:String? = null

            if (year >= 2) {
                stringYearTime = (year.toInt()).toString() + " anos"
            } else {
                stringYearTime = (year.toInt()).toString() + " ano"
            }
            if (month >= 2) {
                stringMonthTime = (month.toInt()).toString() + " meses"
            } else {
                stringMonthTime = (month.toInt()).toString() + " mês"
            }
            if (day >= 2) {
                stringDayTime = (day.toInt()).toString() + " dias"
            } else {
                stringDayTime = (day.toInt()).toString() + " dia"
            }

            if(year > 1 && month > 1 && day > 1){
                stringFullTime = stringYearTime + "\n" + stringMonthTime + "\n" + stringDayTime
            }else if(year > 1 && month > 1){
                stringFullTime = stringYearTime + "\n" + stringMonthTime
            }else if(month > 1 && day > 1 ){
                stringFullTime = stringMonthTime + "\n" + stringDayTime
            }else if(year > 1 && day > 1){
                stringFullTime = stringYearTime + "\n" + stringDayTime
            }else{
                stringFullTime = stringDayTime
            }
            //stringFullTime = stringYearTime +"\n" + stringMonthTime + "\n"+ stringDayTime
            stringTranspTime.text = stringFullTime
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
        if(savedUserBloodPosition != null){
            user_blood.text = bloodOptions.get(savedUserBloodPosition)
        }
        if(savedUserImageName !=null){
            userImageName = savedUserImageName
        }
    }

    fun saveUserImage(bitmap: Bitmap){
        val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath)

        if(!dir.exists()){
            dir.mkdir()
        }
        var fileName = "UserImage.jpeg"
        val outfile = File(dir, fileName)
        var outputStream: FileOutputStream? = null
        try{
            outputStream= FileOutputStream(outfile)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
            outputStream.flush()
            outputStream.close()
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
    fun loadImage(image: ImageView, path:String){
        val circleImageView = image as CircleImageView
        circleImageView.borderWidth = 8
        val bitmap: Bitmap? = try{
            BitmapFactory.decodeFile(path + File.separator + "UserImage.jpeg")
        }catch (e:Exception){
            null
        }
        if(bitmap != null){
            image.setImageBitmap(bitmap)
        }
    }

    fun saveUserImageName(userImageName:String, sharedPrefs: SharedPreferences){
        val editor: SharedPreferences.Editor = sharedPrefs.edit()
        editor.apply {
            putString("user_image_name",userImageName?.toString())
        }.apply()

    }
}