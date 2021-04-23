package com.example.mytrashyapp.data.network

import com.example.mytrashyapp.data.responses.SongsResp
import com.example.mytrashyapp.library.screens.genres.models.Genre
import retrofit2.http.GET

interface MusicApi {
    @GET("music/songs")
    suspend fun getSongs(): SongsResp

    @GET("music/genres")
    suspend fun getGenres(): Genre


}