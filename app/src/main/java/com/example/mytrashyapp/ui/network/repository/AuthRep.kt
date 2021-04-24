package com.example.mytrashyapp.ui.network.repository

import com.example.mytrashyapp.ui.network.AuthApi

class AuthRep(
        private val api: AuthApi
): BaseRep() {
    suspend fun login(token: String) = safeApiCall {
        api.login(token)
    }
}