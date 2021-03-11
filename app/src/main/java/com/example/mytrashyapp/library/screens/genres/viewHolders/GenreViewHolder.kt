package com.example.mytrashyapp.library.screens.genres.viewHolders

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrashyapp.databinding.GenreBinding
import com.example.mytrashyapp.library.screens.genres.models.Genre
import com.example.mytrashyapp.library.screens.songs.adapters.SongRecycleViewAdapter
import com.example.mytrashyapp.library.screens.songs.models.Song

class GenreViewHolder (private val binding: GenreBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(genre: Genre) {
        binding.genre.text = genre.name
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                    binding.recyclerView.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
            )
            adapter = SongRecycleViewAdapter(genre.songs)
            setRecycledViewPool(RecyclerView.RecycledViewPool())
        }
    }
}