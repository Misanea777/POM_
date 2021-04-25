package com.example.mytrashyapp.ui.library.screens.songs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrashyapp.R
import com.example.mytrashyapp.databinding.FragmentSongsBinding

import com.example.mytrashyapp.ui.library.screens.songs.adapters.SongRecycleViewAdapter
import com.example.mytrashyapp.ui.library.screens.songs.models.Song


class SongsFragment : Fragment() {

    private lateinit var binding: FragmentSongsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: SongRecycleViewAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSongsBinding.inflate(layoutInflater)
        return binding.getRoot()
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {

        val dataSet = Array(30) {
            Song(
                    R.drawable.music_logo,
                    "Unkonwn Song",
                    "DJ Misanea"
            )
        }

        viewManager = LinearLayoutManager(activity)
        viewAdapter = SongRecycleViewAdapter(dataSet)
        recyclerView = binding.songRecycleView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

}