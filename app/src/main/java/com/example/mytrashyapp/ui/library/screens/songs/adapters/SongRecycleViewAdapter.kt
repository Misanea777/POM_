package com.example.mytrashyapp.ui.library.screens.songs.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrashyapp.databinding.SongBinding
import com.example.mytrashyapp.services.MusicService
import com.example.mytrashyapp.ui.MainActivity
import com.example.mytrashyapp.ui.library.screens.songs.models.Song
import com.example.mytrashyapp.ui.library.screens.songs.viewHolders.SongViewHolder
import com.example.mytrashyapp.ui.library.screens.songs.viewHolders.listen
import com.example.mytrashyapp.util.Constants.Companion.PLAY
import com.example.mytrashyapp.util.Constants.Companion.SONG_ARTIST
import com.example.mytrashyapp.util.Constants.Companion.SONG_NAME
import java.net.URL

class SongRecycleViewAdapter(private var dataSet: Array<Song>) : RecyclerView.Adapter<SongViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding =SongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )

        return SongViewHolder(binding).listen {positon, type ->
            val item = dataSet[positon]
            println("sucaaaaaa ${item.name}")

            val intent = Intent(PLAY, Uri.parse(item.url), parent.context, MusicService::class.java )
            val extras = Bundle()
            extras.putString(SONG_NAME, item.name)
            extras.putString(SONG_ARTIST, item.artist)
            intent.putExtras(extras)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                parent.context.startForegroundService (intent)
            } else {
                parent.context.startService (intent)
            }
        }
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun updateDataSet(dataSet: Array<Song>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }


}