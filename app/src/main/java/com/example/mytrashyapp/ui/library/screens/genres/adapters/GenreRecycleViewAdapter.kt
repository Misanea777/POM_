package com.example.mytrashyapp.ui.library.screens.genres.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrashyapp.databinding.GenreBinding
import com.example.mytrashyapp.ui.library.screens.genres.models.Genre
import com.example.mytrashyapp.ui.library.screens.genres.viewHolders.GenreViewHolder

class GenreRecycleViewAdapter(private var dataSet: Array<Genre>) : RecyclerView.Adapter<GenreViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = GenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}