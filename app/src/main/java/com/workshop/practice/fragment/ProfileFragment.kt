package com.workshop.practice.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.workshop.practice.R


class ProfileFragment : Fragment() {
    lateinit var tvName: TextView
    lateinit var tvDetails: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_profile, container, false)

        tvName= view.findViewById(R.id.tvName)
        tvDetails =view.findViewById(R.id.tvDetails)




        return view
    }

}