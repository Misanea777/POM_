package com.example.mytrashyapp.data.repository

import com.example.mytrashyapp.data.network.MusicApi

class MusicRep(
        private val api: MusicApi
): BaseRep() {
    suspend fun getSongs() = safeApiCall {
        api.getSongs()
    }
}