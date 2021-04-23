package com.example.mytrashyapp.data.repository

import com.example.mytrashyapp.data.network.AuthApi
import com.example.mytrashyapp.data.preferences.UserPreferences

class AuthRep(
        private val api: AuthApi,
        private val preferences: UserPreferences
): BaseRep() {
    suspend fun login(token: String) = safeApiCall {
        api.login(token)
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }
}