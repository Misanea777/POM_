package com.example.mytrashyapp.data.network.repository

import com.example.mytrashyapp.data.network.AuthApi

class AuthRep(
        private val api: AuthApi
): BaseRep() {
    suspend fun login(token: String) = safeApiCall {
        api.login(token)
    }
}