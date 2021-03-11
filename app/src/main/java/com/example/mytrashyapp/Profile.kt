package com.example.mytrashyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation


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