package com.example.mytrashyapp.ui.library.screens.songs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytrashyapp.data.network.Resource
import com.example.mytrashyapp.data.repository.MusicRep
import com.example.mytrashyapp.data.responses.SongsResp
import kotlinx.coroutines.launch

class SongsViewModel(
        private val repository: MusicRep
): ViewModel() {
    private val _songs: MutableLiveData<Resource<SongsResp>> = MutableLiveData()
    val songs: LiveData<Resource<SongsResp>>
        get() = _songs

    fun getSongs() = viewModelScope.launch {
        _songs.value = repository.getSongs()
    }
}