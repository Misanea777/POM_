package com.example.mytrashyapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytrashyapp.data.network.Resource
import com.example.mytrashyapp.data.repository.AuthRep
import com.example.mytrashyapp.data.responses.LoginResp
import kotlinx.coroutines.launch

class AuthViewModel(
        private val repository: AuthRep
): ViewModel() {

    private val _loginResponse: MutableLiveData<Resource<LoginResp>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResp>>
        get() = _loginResponse

    fun login(
        email: String,
        pswd: String
    ) = viewModelScope.launch {
        _loginResponse.value = repository.login(email)
    }

    fun saveAuthToken(token: String) = viewModelScope.launch {
        repository.saveAuthToken(token)
    }

}