package com.example.mytrashyapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytrashyapp.network.repository.AuthRep
import com.example.mytrashyapp.network.repository.BaseRep
import com.example.mytrashyapp.ui.auth.AuthViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val repository: BaseRep
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRep) as T
            else -> throw IllegalArgumentException("ViewModelClass not found")
        }
    }

}