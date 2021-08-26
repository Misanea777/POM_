package com.example.mytrashyapp.ui.library.screens.songs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytrashyapp.data.local.preferences.UserPreferences
import com.example.mytrashyapp.data.model.Songs
import com.example.mytrashyapp.data.repository.AuthRepository
import com.example.mytrashyapp.data.repository.MusicRepository
import com.example.mytrashyapp.ui.MainActivity
import com.example.mytrashyapp.ui.library.screens.songs.models.Song
import com.example.mytrashyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongsViewModel @Inject constructor(
    private val repository: MusicRepository,
    private val preferences: UserPreferences
): ViewModel() {
    private val _songsList: MutableLiveData<Resource<Songs>> = MutableLiveData()
    val songsList: LiveData<Resource<Songs>>
        get() = _songsList

//    fun getSongs(sogsNumber: Int) = viewModelScope.launch {
//        _songsList.value = Resource.Loading
//        _songsList.value = repository.getSongs(sogsNumber)
//    }

    fun getSongs(sogsNumber: Int) = viewModelScope.launch {
        _songsList.value = Resource.Loading
        _songsList.value = Resource.Success(Songs(preferences.songsInfo.first()))
    }

}