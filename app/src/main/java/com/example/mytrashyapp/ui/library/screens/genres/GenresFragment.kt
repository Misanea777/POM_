package com.example.mytrashyapp.ui.library.screens.genres

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrashyapp.R
import com.example.mytrashyapp.databinding.FragmentGenresBinding
import com.example.mytrashyapp.ui.library.screens.genres.adapters.GenreRecycleViewAdapter
import com.example.mytrashyapp.ui.library.screens.genres.models.Genre
import com.example.mytrashyapp.ui.library.screens.songs.models.Song


class GenresFragment : Fragment() {

    private lateinit var binding: FragmentGenresBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: GenreRecycleViewAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGenresBinding.inflate(layoutInflater)
        return binding.getRoot()
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {

        val songsDataSet = Array(30) {
            Song(
                    R.drawable.music_logo,
                    "Unkonwn Song",
                    "DJ Misanea"
            )
        }

        val dataSet = Array(30) {
            Genre(
                    "Manele",
                    songsDataSet
            )
        }

        viewManager = LinearLayoutManager(activity)
        viewAdapter = GenreRecycleViewAdapter(dataSet)
        recyclerView = binding.genreRecycleView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

}