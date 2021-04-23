package com.example.mytrashyapp.data.network

import com.example.mytrashyapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSrc {
    companion object{
        private const val BASE_URL = "http://92.115.190.64:8080/"
    }

    fun<Api> buildApi(
            api: Class<Api>,
            authToken: String? = null
    ): Api   {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(
                    OkHttpClient.Builder()
                            .addInterceptor {chain ->  
                                chain.proceed(chain.request().newBuilder().also {
                                    it.addHeader("Authorization", "Bearer $authToken")
                                }.build())
                            }.also { client ->
                                if(BuildConfig.DEBUG) {
                                    val logging = HttpLoggingInterceptor()
                                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                                    client.addInterceptor(logging)
                                }
                    }.build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(api)
    }
}