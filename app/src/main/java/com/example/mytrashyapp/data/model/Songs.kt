package com.example.mytrashyapp.data.model

import com.example.mytrashyapp.ui.library.screens.songs.models.Song
import com.google.gson.annotations.SerializedName

data class Songs(
    @SerializedName("songs")
    val songs: List<Song>
)