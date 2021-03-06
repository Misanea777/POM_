package com.example.mytrashyapp.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data_store")

class UserPreferences(
        context: Context
) {
    private val applicationContext = context.applicationContext
    val authToken: Flow<String?>
        get() = applicationContext.dataStore.data.map { user_data_store ->
            user_data_store[KEY_AUTH]
        }


    suspend fun saveAuthToken(authToken: String) {
        applicationContext.dataStore.edit { user_data_store ->
            user_data_store[KEY_AUTH] = authToken
        }
    }

    val uiMode: Flow<Boolean>
        get() = applicationContext.dataStore.data.map { user_data_store ->
                user_data_store[UI_MODE] ?: false
            }


    suspend fun saveUIMode(uiMode: Boolean) {
        applicationContext.dataStore.edit { user_data_store ->
            user_data_store[UI_MODE] = uiMode
        }
    }

    companion object{
        private val KEY_AUTH = stringPreferencesKey("key_auth")
        private val UI_MODE = booleanPreferencesKey("ui_mode")
    }


}