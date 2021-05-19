package com.example.mytrashyapp.data.remote

import com.example.mytrashyapp.data.model.Songs
import com.example.mytrashyapp.ui.library.screens.genres.models.Genre
import com.example.mytrashyapp.util.Constants.Companion.USER_PATH
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicApi {

    @GET("user/songs/{songsNumber}")
    suspend fun getSongs(
        @Path("songsNumber") number: Int
    ): Songs

    @GET("$USER_PATH/genre/{genreType}")
    suspend fun getGenre(
            @Path("genreType") type: String
    ): Genre

    @GET("$USER_PATH/songs/top")
    suspend fun getTopSongs(): Songs
}