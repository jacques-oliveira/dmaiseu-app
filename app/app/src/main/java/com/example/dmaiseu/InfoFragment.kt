package com.example.dmaiseu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class InfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inssFragment = InssFragment()
        val tfdFragment = TfdFragment()
        val tmoFragment = TmoFragment()
        val btnInss = view.findViewById<Button>(R.id.btnINSS)
        val btnTfd = view.findViewById<Button>(R.id.btnTFD)
        val btnTmo = view.findViewById<Button>(R.id.btnTMO)

        childFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_content, tfdFragment)
            commit()
        }

        btnInss.setOnClickListener {
            childFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_content, inssFragment)
                commit()
            }
        }
        btnTfd.setOnClickListener {
            childFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_content, tfdFragment)
                commit()
            }
        }
        btnTmo.setOnClickListener {
            childFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_content, tmoFragment)
                commit()
            }
        }
    }
}