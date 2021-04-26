package com.example.mytrashyapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytrashyapp.data.model.LoginModel
import com.example.mytrashyapp.data.repository.AuthRepository
import com.example.mytrashyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
        private val repository: AuthRepository
) : ViewModel() {

    private val _loginResponse: MutableLiveData<Resource<LoginModel>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginModel>>
        get() = _loginResponse

    fun login(
            authToken: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(authToken)
    }

    suspend fun saveAuthTokens(authToken: String) {
        repository.saveAuthToken(authToken)
    }
}