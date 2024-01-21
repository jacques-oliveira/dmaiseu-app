package com.example.dmaiseu

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider

class ProfileFragment : Fragment() {
    private lateinit var viewModel:SharedUserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity()).get(SharedUserViewModel::class.java)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userTranspDate = view.findViewById<TextView>(R.id.user_transp_date) as TextView
        val userReturnDate = view?.findViewById<TextView>(R.id.user_return_date) as TextView
        val userRGP = view?.findViewById<TextView>(R.id.user_rgp) as TextView
        val user_name = view?.findViewById<TextView>(R.id.user_name) as TextView
        val user_state = view?.findViewById<TextView>(R.id.user_state) as TextView
        val userHospistal =view?.findViewById<TextView>(R.id.hospital_value) as TextView
        val stringTranspTime = view?.findViewById<TextView>(R.id.stringTranspTime) as TextView
        val userBlood = view?.findViewById<TextView>(R.id.user_blood) as TextView
        val user_image:ImageView = view?.findViewById<ImageView>(R.id.user_image) as ImageView

        val sharedPrefs: SharedPreferences = this.requireActivity().getSharedPreferences("SHARED_PREFS_USER", Context.MODE_PRIVATE)

        viewModel.loadDataProfile(sharedPrefs,user_name,userRGP,user_state, userTranspDate,userHospistal,userReturnDate, stringTranspTime,userBlood)

        viewModel.loadImage(user_image,viewModel.internalFilePath)

        if(userTranspDate.text == null){
            userTranspDate.setOnClickListener {
                Toast.makeText(activity,"Preencha seus dados \n no Menu lateral!",Toast.LENGTH_LONG*2).show()
            }
        }
    }

}