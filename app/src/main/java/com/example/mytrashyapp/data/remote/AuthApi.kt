package com.example.mytrashyapp.data.remote

import com.example.mytrashyapp.data.model.LoginModel
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("user/login")
    suspend fun login(
        @Body token: String
    ): LoginModel
}