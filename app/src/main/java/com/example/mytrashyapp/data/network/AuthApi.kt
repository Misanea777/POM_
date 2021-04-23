package com.example.mytrashyapp.data.network

import com.example.mytrashyapp.data.responses.LoginResp
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

//    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Body token: String
    ): LoginResp

}