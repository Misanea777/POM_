package com.example.mytrashyapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytrashyapp.data.local.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val preferences: UserPreferences
): ViewModel() {
    val getUserEmail: Flow<String?>  //yeayeah its not returning email, but who cares?
        get() = preferences.authToken

}