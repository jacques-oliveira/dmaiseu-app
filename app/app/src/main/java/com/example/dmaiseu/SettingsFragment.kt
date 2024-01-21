package com.example.dmaiseu

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
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
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File
import java.util.Calendar

class SettingsFragment : Fragment(){
    private lateinit var viewModel: SharedUserViewModel
    private lateinit var sharedPrefs: SharedPreferences
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

        val userImage = view?.findViewById<ImageView>(R.id.user_image) as ImageView
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
        sharedPrefs = this.requireActivity().getSharedPreferences("SHARED_PREFS_USER", Context.MODE_PRIVATE)
        val bloodSpinner = view.findViewById<Spinner>(R.id.blood_options)

        val bloodAdapter = ArrayAdapter(requireActivity(),
                            R.layout.custom_spinner_selected,viewModel.bloodOptions)
        bloodAdapter.setDropDownViewResource(R.layout.custom_spinner_options)

        bloodSpinner.adapter = bloodAdapter

        viewModel.loadDataSettings(sharedPrefs,user_name,userRGP,user_state,userTranspDate,userHospistal,userReturnDate, bloodSpinner)
        viewModel.loadImage(userImage,viewModel.internalFilePath)

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

        userImage!!.setOnClickListener{
            deleteDuplicate(viewModel.internalFilePath)
            cropImage()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val userImage = view?.findViewById<ImageView>(R.id.user_image) as ImageView

        if(resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE){
            val imageUri = data?.data

            if(imageUri != null){
                userImage?.setImageURI(data?.data)
                val imageName:String = imageUri?.path?.substringAfterLast("/").toString()
                if(imageName != null){
                    viewModel.userImageName = imageName
                    viewModel.saveUserImage(imageName,sharedPrefs)
                    //Toast.makeText(context,imageName,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun cropImage(){
        ImagePicker.with(this)
            .galleryMimeTypes(arrayOf("image/*","image/jpeg","image/jpg","image/png"))
            .crop()	    			//Crop image(Optional), Check Customization for more option
            .compress(1024)			//Final image size will be less than 1 MB(Optional)
            .maxResultSize(512, 512)	//Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    private fun deleteDuplicate(path:String){
        val file = File(path)
        if(file.exists()){
            file.deleteRecursively()
        }
    }
}