package com.example.mytrashyapp.data.remote

import com.example.mytrashyapp.data.model.Songs
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicApi {

    @GET("user/songs/{songsNumber}")
    suspend fun getSongs(
        @Path("songsNumber") number: Int
    ): Songs
}