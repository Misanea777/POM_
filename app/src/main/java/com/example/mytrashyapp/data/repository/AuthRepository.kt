package com.example.mytrashyapp.data.repository

import com.example.mytrashyapp.data.local.preferences.UserPreferences
import com.example.mytrashyapp.data.remote.AuthApi
import com.example.mytrashyapp.util.SafeApiCall
import javax.inject.Inject

class AuthRepository @Inject constructor(
        private val api: AuthApi,
        private val preferences: UserPreferences
): SafeApiCall {

    suspend fun login(
            token: String,
    ) = safeApiCall {
        api.login(token)
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }

}