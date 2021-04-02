package com.example.mytrashyapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSrc {
    companion object{
        private const val BASE_URL = "http://localhost:8080/"
    }

    fun<Api> buildApi(
            api: Class<Api>
    ): Api   {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(api)
    }
}