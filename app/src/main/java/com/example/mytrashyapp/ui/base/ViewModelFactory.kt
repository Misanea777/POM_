package com.example.mytrashyapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytrashyapp.data.repository.AuthRep
import com.example.mytrashyapp.data.repository.BaseRep
import com.example.mytrashyapp.data.repository.MusicRep
import com.example.mytrashyapp.ui.library.screens.songs.SongsViewModel
import com.example.mytrashyapp.ui.auth.AuthViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val repository: BaseRep
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRep) as T
            modelClass.isAssignableFrom(SongsViewModel::class.java) -> SongsViewModel(repository as MusicRep) as T
            else -> throw IllegalArgumentException("ViewModelClass not found")
        }
    }

}