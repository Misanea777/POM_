package com.example.mytrashyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytrashyapp.databinding.FragmentMusicPlayerBinding


class MusicPlayerFragment : Fragment() {

    private lateinit var binding: FragmentMusicPlayerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicPlayerBinding.inflate(layoutInflater)
        return binding.root
    }


}