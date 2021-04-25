package com.example.mytrashyapp.data.network

import com.example.mytrashyapp.data.network.responses.LoginResp
import com.example.mytrashyapp.ui.library.screens.genres.models.Genre
import com.example.mytrashyapp.ui.library.screens.songs.models.Song
import retrofit2.http.*

interface AuthApi {


    @POST("user/login")
    suspend fun login(
            @Body token: String,
    ): LoginResp

    @GET("user/song")
    suspend fun getSong(): Song


    @GET("user/genre")
    suspend fun getGenre(): Genre
}