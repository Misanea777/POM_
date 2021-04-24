package com.example.mytrashyapp.ui.library.screens.songs.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.mytrashyapp.databinding.SongBinding
import com.example.mytrashyapp.ui.library.screens.songs.models.Song

class SongViewHolder (private val binding: SongBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(song: Song) {
        binding.songName.text = song.name
        binding.songArtist.text = song.artist
        binding.imageView.setImageResource(song.image)
    }
}