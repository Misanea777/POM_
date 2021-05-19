package com.example.mytrashyapp.ui.library.screens.songs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrashyapp.databinding.SongBinding
import com.example.mytrashyapp.ui.library.screens.songs.models.Song
import com.example.mytrashyapp.ui.library.screens.songs.viewHolders.SongViewHolder

class SongRecycleViewAdapter(private var dataSet: Array<Song>) : RecyclerView.Adapter<SongViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding =SongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )

        return SongViewHolder(binding)
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