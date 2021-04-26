package com.example.mytrashyapp.data.remote

import com.example.mytrashyapp.data.model.LoginModel
import com.example.mytrashyapp.util.Constants.Companion.USER_PATH
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("user/login")
    suspend fun login(
        @Body token: String
    ): LoginModel

    @GET("$USER_PATH/getUser")
    suspend fun getUser(): LoginModel
}