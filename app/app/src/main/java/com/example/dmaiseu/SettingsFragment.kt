package com.example.dmaiseu

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
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
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class SettingsFragment : Fragment(){
    private lateinit var viewModel: SharedUserViewModel
    private lateinit var sharedPrefs: SharedPreferences
    private val REQUEST_READ_WRITE_PERMISSION_STORAGE:Int = 2001
    private val REQUEST_PERMISSION_CODE = 123
    private val IMAGE_PICK_REQUEST = 104

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
        sharedPrefs = this.requireActivity().getSharedPreferences("SHARED_PREFS_USER", Context.MODE_PRIVATE)
        val bloodSpinner = view.findViewById<Spinner>(R.id.blood_options)

        val bloodAdapter = ArrayAdapter(requireActivity(),
                            R.layout.custom_spinner_selected,viewModel.bloodOptions)
        bloodAdapter.setDropDownViewResource(R.layout.custom_spinner_options)

        bloodSpinner.adapter = bloodAdapter
        //loadUserImage()
        viewModel.loadImage(userImage)
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
            try {

                val bloodPosition = bloodAdapter.getPosition(bloodSpinner.selectedItem.toString())
                if(viewModel.saveData(sharedPrefs,user_name.text.toString(),userRGP.text.toString(),user_state.text.toString(),
                        userTranspDate.text.toString(),userHospistal.text.toString(),userReturnDate.text.toString(),bloodPosition) ){
                    Toast.makeText(activity, "Dados Salvos com sucesso!",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(activity, "Preencha seus dados!",Toast.LENGTH_LONG).show()
                }
            }catch (ex:Exception){

            }

        }

        userImage!!.setOnClickListener{
            askPermissions()
        }

    }

    fun askPermissions() {
        val permissionCheck =
            activity?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE) };
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                requireActivity(),arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_READ_WRITE_PERMISSION_STORAGE)
        }else{
            startCrop()
        }
    }

    private fun exibirMensagem(message:String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun startCrop(){
        ImagePicker.Companion.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080,1080)
            .start()

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_READ_WRITE_PERMISSION_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, você pode realizar a ação desejada aqui
                exibirMensagem("Permissão Concedida")
            } else {
                // Permissão negada, você pode mostrar uma mensagem ao usuário ou tomar outra ação apropriada
                Toast.makeText(requireContext(), "Permissão negada!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_REQUEST || resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            val userImage = view?.findViewById<ImageView>(R.id.user_image)
            userImage?.setImageURI(uri)
            val bitmap: Bitmap? = uri?.let { MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it) }
            //saveBitmap(bitmap!!)
            saveUserImage(bitmap!!)
        }

    }


    fun saveUserImage(bitmap: Bitmap, fileName: String = "UserImage.jpeg") {

        val resolver = context?.contentResolver
        val folderName = "MyAppImages"
        val relativePath = "Pictures/$folderName"

        // Verifica se a pasta existe e deleta
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val folderUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon()
                .appendQueryParameter("directory", relativePath).build()

            resolver?.delete(folderUri, null, null)
        }

        // Configurações para o novo arquivo no MediaStore
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
        }

        // Insere uma nova entrada no MediaStore e obtém o URI do novo arquivo
        val uri = resolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        uri?.let { outputUri ->
            resolver.openOutputStream(outputUri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
        }
    }

    fun loadUserImage(fileName: String = "UserImage.jpeg"): File? {
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+"/MyAppImages")
        val file = File(directory, fileName)

        // Verifica se o arquivo existe
        return file
    }

}