package com.example.mytrashyapp.ui.library.screens.songs.adapters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.get
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrashyapp.R
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
import com.example.mytrashyapp.util.Constants.Companion.POSITION
import com.example.mytrashyapp.util.Constants.Companion.SET
import com.example.mytrashyapp.util.Constants.Companion.SONGS
import com.example.mytrashyapp.util.Constants.Companion.SONG_ARTIST
import com.example.mytrashyapp.util.Constants.Companion.SONG_NAME
import com.example.mytrashyapp.util.Constants.Companion.SONG_POS
import com.google.android.exoplayer2.ui.PlayerControlView
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.net.URL
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class SongRecycleViewAdapter (private val playerControlView: PlayerControlView?, var dataSet: Array<Song>, var lastPosition: Int) : RecyclerView.Adapter<SongViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = SongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )



        val songViewHolder = SongViewHolder(binding)

        return songViewHolder.listen {position, type ->
            parent.getChildAt(lastPosition)?.isSelected = false
            songViewHolder.itemView.isSelected = true
            lastPosition = position
            execute(parent.context, PLAY, position)
        }
    }

    fun execute(context: Context, command: String, position: Int = 0) {
        val intent = Intent(command, Uri.parse(""), context, MusicService::class.java )
        val songs = dataSet.toCollection(ArrayList())
        intent.putParcelableArrayListExtra(SONGS, songs)
        intent.putExtra(PLAYER_POS, position)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService (intent)
        } else {
            context.startService (intent)
        }
    }


    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(dataSet[position])
        if(lastPosition == -1) {
            execute(holder.itemView.context, SET)
        }
        if(position == lastPosition) {
            holder.itemView.isSelected = true
        }
    }


    override fun getItemCount() = dataSet.size

    fun updateDataSet(dataSet: Array<Song>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }


}