package com.example.mytrashyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytrashyapp.R


class Profile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.song, container, false)
//        val action = ProfileDirections.actionProfileToLibrary()
//        view.findViewById<TextView>(R.id.textView).setOnClickListener { Navigation.findNavController(view).navigate(action)}
        return view
    }

}