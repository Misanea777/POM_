package com.example.mytrashyapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytrashyapp.data.local.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
        private val preferences: UserPreferences
): ViewModel() {

    val uiMode: Flow<Boolean>
        get() = preferences.uiMode

    fun saveUIMode(uiMode: Boolean)  = viewModelScope.launch {
        preferences.saveUIMode(uiMode)
    }
}