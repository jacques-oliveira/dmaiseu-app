package com.example.dmaiseu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userTranspDate = view.findViewById<TextView>(R.id.user_transp_date)

        if(userTranspDate.text == null){

            userTranspDate.setOnClickListener {
                Toast.makeText(activity,"Preencha seus dados \n no Menu lateral!",Toast.LENGTH_LONG*2).show()
            }
        }
    }
}