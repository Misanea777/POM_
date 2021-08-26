package com.example.mytrashyapp.ui.library.screens.songs.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.mytrashyapp.R
import com.example.mytrashyapp.databinding.SongBinding
import com.example.mytrashyapp.ui.library.screens.songs.models.Song

class SongViewHolder (private val binding: SongBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(song: Song) {
        binding.songName.text = song.name
        binding.songArtist.text = song.artist
        binding.imageView.setImageResource(R.drawable.music_logo) // hardcoded so far
    }
}

fun <T : RecyclerView.ViewHolder> T.listen(event: (positon: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}