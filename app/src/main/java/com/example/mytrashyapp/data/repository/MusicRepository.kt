package com.example.mytrashyapp.data.repository

import com.example.mytrashyapp.data.model.Songs
import com.example.mytrashyapp.data.remote.MusicApi
import com.example.mytrashyapp.util.Resource
import com.example.mytrashyapp.util.SafeApiCall
import javax.inject.Inject

class MusicRepository @Inject constructor(
    private val api: MusicApi
): SafeApiCall {
    suspend fun getSongs(songsNumber: Int): Resource<Songs> = safeApiCall {
        api.getSongs(songsNumber)
    }

}