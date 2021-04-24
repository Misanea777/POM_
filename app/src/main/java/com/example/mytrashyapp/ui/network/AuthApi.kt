package com.example.mytrashyapp.ui.network

import com.example.mytrashyapp.ui.network.responses.LoginResp
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
            @Field("token") token: String,
    ): LoginResp

}