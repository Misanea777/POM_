package com.example.mytrashyapp.ui.library.screens.songs.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
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
import com.example.mytrashyapp.util.Constants
import com.example.mytrashyapp.util.Constants.Companion.MUS_SERVICE
import com.example.mytrashyapp.util.Constants.Companion.PLAY
import com.example.mytrashyapp.util.Constants.Companion.PLAYER_POS
import com.example.mytrashyapp.util.Constants.Companion.SET
import com.example.mytrashyapp.util.Constants.Companion.SONGS
import com.example.mytrashyapp.util.Constants.Companion.SONG_ARTIST
import com.example.mytrashyapp.util.Constants.Companion.SONG_NAME
import com.example.mytrashyapp.util.Constants.Companion.SONG_POS
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class SongRecycleViewAdapter(private var dataSet: Array<Song>,val intent: Intent?) : RecyclerView.Adapter<SongViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding =SongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )

        if(!intent?.action.equals(MUS_SERVICE)) {
            execute(parent.context, SET, dataSet.toCollection(ArrayList()))
        }


        return SongViewHolder(binding).listen {position, type ->
            execute(parent.context, PLAY, dataSet.toCollection(ArrayList()), position)
        }
    }

    fun execute(context: Context, command: String, playList:  ArrayList<Song>, position: Int = 0) {
        val intent = Intent(command, Uri.parse(""), context, MusicService::class.java )
        val songs = dataSet.toCollection(ArrayList())
        Collections.rotate(songs, -position)
        intent.putParcelableArrayListExtra(SONGS, songs)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService (intent)
        } else {
            context.startService (intent)
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