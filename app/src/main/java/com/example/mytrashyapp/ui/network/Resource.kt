package com.example.mytrashyapp.ui.network

import okhttp3.ResponseBody

sealed class Resource<out T> {
    data class Success<out T>(val value: T): Resource<T>()
    data class Failure(
            val isNetworkErr: Boolean,
            val errCode: Int?,
            val errBody: ResponseBody?
    ): Resource<Nothing>()
}