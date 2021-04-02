package com.example.mytrashyapp.network.repository

import com.example.mytrashyapp.network.AuthApi

class AuthRep(
        private val api: AuthApi
): BaseRep() {
    suspend fun login(token: String) = safeApiCall {
        api.login(token)
    }
}